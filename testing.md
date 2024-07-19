# Testing

## 1 How to test

Writing tests is an essential part of design and implementation. The most important skill in writing tests is to determine what to test, and then determine how to test.

## 2 Examples

Imagine you have an interface `IFunctionality` and you must provide its implementation `ConcreteFunctionality`. You must check that your `ConcreteFunctionality` implementation of the `IFunctionality` interface works as specified. However thinking about tests after you have completed the implementation is not ideal. Since you have already written your implementation, you will likely come up with tests that you already know will pass, rather than tests that should pass. Here are some recommendations on how to come up with effective test cases:

* Follow this workflow: *Write interface* > *Write an empty implementation* > *Write test cases*. Writing an empty implementation (all the methods are present, but empty) will ensure that referring to the implementation class in your test cases does not produce compiling errors. Fill in the implementation *after* writing your test cases.
* Convince yourself that the code to be tested cannot be trusted, and it's up to you to find any mistakes. Often a role reversal helps: imagine the instructor was writing code for the homework (as was done on 1), and you get credit for finding mistakes in the instructor's code! Be creative: where might the gotchas be in the design, and how might someone else misunderstand or mis-implement the design?
* Look at each method of the interface in isolation. Think about what behavior you expect when all inputs are *correct* and *as expected* (if you wrote the interface be sure to document its intended behavior when you are writing it!). Remember that a test passes if the *expected behavior* is the same as the *actual behavior*.
* Look at each method of the interface in isolation. Think about every possibility of passing correct and incorrect parameters, and figure out how to check for whether the method behaves as expected in each situation. Carefully read about its objectives, including exceptional cases. Think about how you will verify that an implementation of that method actually fulfills these objectives. How will you reproduce exceptional cases so that you can test them?
* Now think about the sequences in which various methods of the interface might be called. Is there a ''correct" sequence of calling them? What happens when they are called ''out of sequence," and what should happen?
* Remember that the best option is to catch incorrect uses of your code at compile time, such that client code that incorrectly uses your code will produce compile-time errors. This ensures that if someone uses your code incorrectly, they cannot even compile their program. The next best option is to flag incorrect uses at run time (e.g. using exceptions). This ensures that client code will produce errors when it is run, because it used your code incorrectly. The unacceptable option is to hope that everybody will read your documentation (or worse, your code), understand it, and use it accordingly---and therefore convince yourself there's no need to actually put checks in your code. Remember that if documentation is vague somebody will misinterpret it, if functionality does not consider some specific scenario somebody will produce it. Better your tests than the user.

Writing tests before writing your implementation will give you insight into what your implementation ought to do. Moreover, it will help you work through the types and visibilities of your interfaces, classes, and methods, and often just understanding that structure is a big help in understanding the problem.

## 3 Testing

Obviously, unfortunately, you often can't write a *complete* set of tests for your code before you've started writing your code, as the process of implementing a design can easily bring issues to the forefront that you didn't notice or anticipate. Proper testing is an iterative process: starting from initial examples you create an initial implementation, which might suggest additional tests, which might cause test failures, which you need to fix, which might suggest additional tests, etc. A successful set of test cases is one that tests whether your implementation adheres to your design, whether your design leaves loopholes and ambiguities that allow its incorrect usage, and whether the behavior of your implementation can be predicted in all situations. This set of test cases should *compile*, and upon running, should *pass*.

**NOTE**: It is far better to include tests that you know to fail, rather than comment them out or delete them. Leave a `// FIXME` comment next to the failing tests, explaining what you intended the test to check for, and why you think it's currently failing. At some point you clearly had a reason for writing the test case, and it would be a shame to lose that insight by deleting the test! Equally bad is commenting the test out, since it gives the misleading impression that everything is fine and all tests pass, when there are known problems remaining...

### 3.1 Kinds of tests

There are many kinds of tests you may wish to write:

* **Unit tests** are the style of tests we've written all along: they test the smallest components of your program---individual functions, classes, or interfaces, for example---and confirm that they work as expected. Unit tests are useful for confirming that edge cases are properly handled, that algorithms seem to work as expected on their inputs, etc.

![Unit Tests](img/unit_tests.png)

* **Regression tests** are the kinds of tests you always regret not having written sooner. They are written as soon as you notice a bug in your code and fix it: their purpose is to ensure that the bug can never creep back into your program inadvertently. Write regression tests even for the simplest of bugs: if you were inattentive enough to make that mistake once, you could make it again, and so could your colleagues. Let them, and your future self, benefit from noticing the bug now!

![Regression](img/Dont-Overlook-Your-Regression-Testing.jpg)

* **Integration tests** test larger units of functionality, or indeed even libraries at a time. They are trickier to write, because their inputs are usually larger and more structured: for instance, testing that a sequence of user inputs produces the correct sequence of outputs. We will discuss these more in the future.

![Integration](img/unit-tests-passing-no-integration-tests.jpg)

* **Randomized or ''fuzz" tests** are designed to rapidly explore a wider space of potential inputs than can easily be written manually. Typically these tests require writing the code to be tested (obviously!), the code to randomly generate inputs, and either a secondary implementation of the code being tested or a predicate that can confirm the proper operation of that code. These latter two are known as oracles , because they never make mistakes, but you have to interpret their results carefully. Fuzz testing is fantastic for checking (for example) the robustness of the error handling of your program, to see whether it holds up without crashing even under truly odd inputs. (Fuzz testing with malicious intent is one of the tools hackers use to exploit weaknesses in systems.)

![Randomized](img/random_number.png)

In addition, you may also wish to test the performance of your application: that some scenario completes within a certain time limit. You may find that using the [timeout attribute](https://junit.org/junit5/docs/5.5.1/api/org/junit/jupiter/api/Timeout.html) on `@Test` annotations is very helpful for this. (Your test method should still have some assertions in it to confirm that your code does do what it's supposed to do; after all, a method that does absolutely nothing will indeed run quickly! Running quickly does not imply running correctly.)

## 3.2 Where do tests go in my project?

Java's package mechanisms allow for careful compartmentalizing of your code, to ensure that it neither relies upon nor modifies anything it should not have access to. This applies equally to your tests. Tests belong in a separate test/ directory, whose subdirectories parallel the structure of your src/ directory. Where specific tests belong depends on their intent:

* If a test is checking for the internal behavior of a class---i.e. internal invariants, or methods that are not exposed to clients---then the test belongs in the same package as the code it is testing. Note that you should *not* make fields or helper methods `public` simply to make them easily testable: by placing the tests in the same package as the code, the tests will automatically be able to see those fields and methods, while leaving them properly encapsulated for clients.
* If a test is checking for the public behavior of an interface or library, then it should ***not*** be placed in the same package as the code you are testing, but rather placed in the default package. This will ensure that your test is ''seeing" the code exactly as other client code will.
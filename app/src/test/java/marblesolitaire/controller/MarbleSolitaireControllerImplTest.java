package marblesolitaire.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import marblesolitaire.model.hw02.EnglishSolitaireModel;
import marblesolitaire.model.hw04.TriangleSolitaireModel;
import marblesolitaire.view.MarbleSolitaireTextView;
import marblesolitaire.view.TriangleSolitaireTextView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MarbleSolitaireControllerImplTest {

  EnglishSolitaireModel model3x3;
  MarbleSolitaireTextView textView3x3;
  MarbleSolitaireControllerImpl controller3x3Append;
  Appendable stringBuilder;
  Readable userInputReader;
  String startMessage;
  String starting3x3board;

  TriangleSolitaireModel triangleModel5;
  TriangleSolitaireTextView triangleView;
  MarbleSolitaireControllerImpl triangleController;
  Appendable triangleStringBuilder;
  Readable triangleUserInputReader;
  String startingTriangleBoard;


  @BeforeEach
  void setUp() {
    model3x3 = new EnglishSolitaireModel();
    stringBuilder = new StringBuilder();
    textView3x3 = new MarbleSolitaireTextView(model3x3, stringBuilder);
    startMessage = "Move by inputting from row, from col, to row, to col, \n" +
            "in that order or all in one line (row/col starts at position 1 1). \n" +
            "Type input: ";
    starting3x3board = "    O O O\n" +
            "    O O O\n" +
            "O O O O O O O\n" +
            "O O O _ O O O\n" +
            "O O O O O O O\n" +
            "    O O O\n" +
            "    O O O";

    triangleModel5 = new TriangleSolitaireModel();
    triangleStringBuilder = new StringBuilder();
    triangleView = new TriangleSolitaireTextView(triangleModel5, triangleStringBuilder);
    startingTriangleBoard = "    _\n" +
            "   O O\n" +
            "  O O O\n" +
            " O O O O\n" +
            "O O O O O";

  }

  /**
   * Initializes a new controller object, with user input
   *
   * @param userInput string that user types
   */
  void initializeController(String userInput) {
    userInputReader = new StringReader(userInput);
    stringBuilder = new StringBuilder();
    textView3x3 = new MarbleSolitaireTextView(model3x3, stringBuilder);
    controller3x3Append = new MarbleSolitaireControllerImpl(model3x3, textView3x3, userInputReader);


    triangleUserInputReader = new StringReader(userInput);
    triangleStringBuilder = new StringBuilder();
    triangleView = new TriangleSolitaireTextView(triangleModel5, triangleStringBuilder);
    triangleController = new MarbleSolitaireControllerImpl(triangleModel5, triangleView, userInputReader);
  }

  /**
   * Test ControllerImpl constructor
   */
  @Test
  void testConstructor() {
    //Test if model is null
    assertThrows(IllegalArgumentException.class, () -> {
      new MarbleSolitaireControllerImpl(null, this.textView3x3, this.userInputReader);
    });

    //Test if view is null
    assertThrows(IllegalArgumentException.class, () -> {
      new MarbleSolitaireControllerImpl(this.model3x3, null, this.userInputReader);
    });

    //Test if readable is null
    assertThrows(IllegalArgumentException.class, () -> {
      new MarbleSolitaireControllerImpl(this.model3x3, this.textView3x3, null);
    });

  }

  //test if playGame renders initial board and messages correctly
  @Test
  void initialGame() throws IOException {
    this.initializeController("");
    assertEquals("", this.stringBuilder.toString());
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage,
            this.stringBuilder.toString());

    //Triangle Test
    this.setUp();
    this.initializeController("");
    this.triangleController.playGame();
    assertEquals(this.startingTriangleBoard + "\nScore: " + "14"
            + "\n" + this.startMessage, this.triangleStringBuilder.toString());
  }

  //test if playGame renders valid user input
  //with four numbers in one line format, e.g: (4 2 4 4)
  @Test
  void testValidFourNumberInput() {

    //test left to right move
    this.initializeController("4 2 4 4");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage
                    + "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O _ _ O O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 31",
            this.stringBuilder.toString());

    //test up to down move
    this.setUp();
    this.initializeController("2 4 4 4");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage
                    + "    O O O\n" +
                    "    O _ O\n" +
                    "O O O _ O O O\n" +
                    "O O O O O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 31",
            this.stringBuilder.toString());

    //test right to left move
    this.setUp();
    this.initializeController("4 6 4 4");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage
                    + "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O O O O _ _ O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 31",
            this.stringBuilder.toString());

    //test down to up move
    this.setUp();
    this.initializeController("6 4 4 4");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage
                    + "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O O O O O O O\n" +
                    "O O O _ O O O\n" +
                    "    O _ O\n" +
                    "    O O O\n" +
                    "Score: 31",
            this.stringBuilder.toString());

    //test multiple moves in different directions
    this.setUp();
    this.initializeController("4 2 4 4 \n 6 3 4 3 \n 5 5 5 3");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage
                    + "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O _ _ O O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 31"
                    + "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O _ O O O O O\n" +
                    "O O _ O O O O\n" +
                    "    _ O O\n" +
                    "    O O O\n" +
                    "Score: 30"
                    + "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O _ O O O O O\n" +
                    "O O O _ _ O O\n" +
                    "    _ O O\n" +
                    "    O O O\n" +
                    "Score: 29",
            this.stringBuilder.toString());

    //Test triangle
    this.setUp();
    this.initializeController("3 3 1 5");
    this.triangleController.playGame();
    assertEquals(this.startingTriangleBoard + "\nScore: " + "14"
            + "\n" + this.startMessage + "    O\n" +
            "   _ O\n" +
            "  _ O O\n" +
            " O O O O\n" +
            "O O O O O\n" +
            "Score: 13", this.triangleStringBuilder.toString());


  }

  //Test if playGame renders valid user input
  //with four numbers in separate line format
  @Test
  void testValidOneNumberInput() {

    //User inputs only one number, board should not update
    //because move is not finished
    this.initializeController("6");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage,
            this.stringBuilder.toString());

    //User inputs only two numbers on separate lines, board should not update
    //because move is not finished
    this.initializeController("6 \n 4");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage,
            this.stringBuilder.toString());


    //User inputs only three numbers on separate lines, board should not update
    //because move is not finished
    this.initializeController("6 \n 4 \n 4");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage,
            this.stringBuilder.toString());

    //User inputs four numbers on separate lines, board should update to show finished move
    this.initializeController("6 \n 4 \n 4 \n 4");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage
                    + "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O O O O O O O\n" +
                    "O O O _ O O O\n" +
                    "    O _ O\n" +
                    "    O O O\n" +
                    "Score: 31",
            this.stringBuilder.toString());

    //When user inputs invalid input in middle of move, and then
    //inputs valid to complete move
    this.setUp();
    this.initializeController("4 \n 2 \n a \n 4 \n 4 ");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage +
                    "Invalid input, try again: " +
                    "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O _ _ O O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 31",
            this.stringBuilder.toString());

    //Test triangle
    this.setUp();
    this.initializeController("3 \n 3 \n 1 \n 5");
    this.triangleController.playGame();
    assertEquals(this.startingTriangleBoard + "\nScore: " + "14"
            + "\n" + this.startMessage + "    O\n" +
            "   _ O\n" +
            "  _ O O\n" +
            " O O O O\n" +
            "O O O O O\n" +
            "Score: 13", this.triangleStringBuilder.toString());

  }

  //Test when user quits game
  @Test
  void testQuitGame() {

    //Test when user quits game before any other input with lowercase q
    this.initializeController("q");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage + "Game quit!\n" +
                    "State of game when quit:\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O O O _ O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 32",
            this.stringBuilder.toString());

    //Test when user quits game before any other input, with uppercase Q
    this.initializeController("Q");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage + "Game quit!\n" +
                    "State of game when quit:\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O O O _ O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 32",
            this.stringBuilder.toString());

    //Test when user quits game after trying to make a move
    this.initializeController("4 \n q");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage + "Game quit!\n" +
                    "State of game when quit:\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O O O _ O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 32",
            this.stringBuilder.toString());

    //Test when user quits game after making a move
    this.initializeController("4 \n 2 \n 4 \n 4 \n 4 \n q");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage + "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O _ _ O O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 31Game quit!\n" +
                    "State of game when quit:\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O _ _ O O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 31",
            this.stringBuilder.toString());

    //test triangle
    this.setUp();
    this.initializeController("3 3 1 5 \n q");
    this.triangleController.playGame();
    assertEquals(this.startingTriangleBoard + "\nScore: " + "14"
            + "\n" + this.startMessage + "    O\n" +
            "   _ O\n" +
            "  _ O O\n" +
            " O O O O\n" +
            "O O O O O\n" +
            "Score: 13" + "Game quit!\n" +
            "State of game when quit:\n" +
            "    O\n" +
            "   _ O\n" +
            "  _ O O\n" +
            " O O O O\n" +
            "O O O O O\n" +
            "Score: 13", this.triangleStringBuilder.toString());
  }

  //Test when game is over
  @Test
  void testGameOver() {
    //User input that results in game being over
    this.initializeController("4 6 4 4 \n" +
            "4 3 4 5 \n" +
            "4 1 4 3 \n" +
            "6 4 4 4 \n" +
            "4 4 4 6 \n" +
            "4 7 4 5 \n" +
            "2 4 4 4 \n" +
            "4 4 4 6 \n" +
            "6 5 4 5 \n" +
            "5 7 5 5 \n" +
            "4 5 6 5 \n" +
            "7 5 5 5 \n" +
            "3 6 3 4 \n" +
            "3 3 3 5 \n" +
            "2 5 4 5 \n" +
            "5 3 3 3 \n" +
            "3 2 3 4 \n" +
            "1 3 3 3 \n" +
            "3 4 3 2 \n" +
            "3 1 3 3 \n" +
            "5 1 5 3 \n" +
            "4 5 6 5 \n" +
            "6 3 4 3 \n" +
            "3 3 5 3 \n" +
            "1 5 1 3 \n");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage
                    + "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O O O O _ _ O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 31    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O O _ _ O _ O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 30    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "_ _ O _ O _ O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 29    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "_ _ O O O _ O\n" +
                    "O O O _ O O O\n" +
                    "    O _ O\n" +
                    "    O O O\n" +
                    "Score: 28    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "_ _ O _ _ O O\n" +
                    "O O O _ O O O\n" +
                    "    O _ O\n" +
                    "    O O O\n" +
                    "Score: 27    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "_ _ O _ O _ _\n" +
                    "O O O _ O O O\n" +
                    "    O _ O\n" +
                    "    O O O\n" +
                    "Score: 26    O O O\n" +
                    "    O _ O\n" +
                    "O O O _ O O O\n" +
                    "_ _ O O O _ _\n" +
                    "O O O _ O O O\n" +
                    "    O _ O\n" +
                    "    O O O\n" +
                    "Score: 25    O O O\n" +
                    "    O _ O\n" +
                    "O O O _ O O O\n" +
                    "_ _ O _ _ O _\n" +
                    "O O O _ O O O\n" +
                    "    O _ O\n" +
                    "    O O O\n" +
                    "Score: 24    O O O\n" +
                    "    O _ O\n" +
                    "O O O _ O O O\n" +
                    "_ _ O _ O O _\n" +
                    "O O O _ _ O O\n" +
                    "    O _ _\n" +
                    "    O O O\n" +
                    "Score: 23    O O O\n" +
                    "    O _ O\n" +
                    "O O O _ O O O\n" +
                    "_ _ O _ O O _\n" +
                    "O O O _ O _ _\n" +
                    "    O _ _\n" +
                    "    O O O\n" +
                    "Score: 22    O O O\n" +
                    "    O _ O\n" +
                    "O O O _ O O O\n" +
                    "_ _ O _ _ O _\n" +
                    "O O O _ _ _ _\n" +
                    "    O _ O\n" +
                    "    O O O\n" +
                    "Score: 21    O O O\n" +
                    "    O _ O\n" +
                    "O O O _ O O O\n" +
                    "_ _ O _ _ O _\n" +
                    "O O O _ O _ _\n" +
                    "    O _ _\n" +
                    "    O O _\n" +
                    "Score: 20    O O O\n" +
                    "    O _ O\n" +
                    "O O O O _ _ O\n" +
                    "_ _ O _ _ O _\n" +
                    "O O O _ O _ _\n" +
                    "    O _ _\n" +
                    "    O O _\n" +
                    "Score: 19    O O O\n" +
                    "    O _ O\n" +
                    "O O _ _ O _ O\n" +
                    "_ _ O _ _ O _\n" +
                    "O O O _ O _ _\n" +
                    "    O _ _\n" +
                    "    O O _\n" +
                    "Score: 18    O O O\n" +
                    "    O _ _\n" +
                    "O O _ _ _ _ O\n" +
                    "_ _ O _ O O _\n" +
                    "O O O _ O _ _\n" +
                    "    O _ _\n" +
                    "    O O _\n" +
                    "Score: 17    O O O\n" +
                    "    O _ _\n" +
                    "O O O _ _ _ O\n" +
                    "_ _ _ _ O O _\n" +
                    "O O _ _ O _ _\n" +
                    "    O _ _\n" +
                    "    O O _\n" +
                    "Score: 16    O O O\n" +
                    "    O _ _\n" +
                    "O _ _ O _ _ O\n" +
                    "_ _ _ _ O O _\n" +
                    "O O _ _ O _ _\n" +
                    "    O _ _\n" +
                    "    O O _\n" +
                    "Score: 15    _ O O\n" +
                    "    _ _ _\n" +
                    "O _ O O _ _ O\n" +
                    "_ _ _ _ O O _\n" +
                    "O O _ _ O _ _\n" +
                    "    O _ _\n" +
                    "    O O _\n" +
                    "Score: 14    _ O O\n" +
                    "    _ _ _\n" +
                    "O O _ _ _ _ O\n" +
                    "_ _ _ _ O O _\n" +
                    "O O _ _ O _ _\n" +
                    "    O _ _\n" +
                    "    O O _\n" +
                    "Score: 13    _ O O\n" +
                    "    _ _ _\n" +
                    "_ _ O _ _ _ O\n" +
                    "_ _ _ _ O O _\n" +
                    "O O _ _ O _ _\n" +
                    "    O _ _\n" +
                    "    O O _\n" +
                    "Score: 12    _ O O\n" +
                    "    _ _ _\n" +
                    "_ _ O _ _ _ O\n" +
                    "_ _ _ _ O O _\n" +
                    "_ _ O _ O _ _\n" +
                    "    O _ _\n" +
                    "    O O _\n" +
                    "Score: 11    _ O O\n" +
                    "    _ _ _\n" +
                    "_ _ O _ _ _ O\n" +
                    "_ _ _ _ _ O _\n" +
                    "_ _ O _ _ _ _\n" +
                    "    O _ O\n" +
                    "    O O _\n" +
                    "Score: 10    _ O O\n" +
                    "    _ _ _\n" +
                    "_ _ O _ _ _ O\n" +
                    "_ _ O _ _ O _\n" +
                    "_ _ _ _ _ _ _\n" +
                    "    _ _ O\n" +
                    "    O O _\n" +
                    "Score: 9    _ O O\n" +
                    "    _ _ _\n" +
                    "_ _ _ _ _ _ O\n" +
                    "_ _ _ _ _ O _\n" +
                    "_ _ O _ _ _ _\n" +
                    "    _ _ O\n" +
                    "    O O _\n" +
                    "Score: 8    O _ _\n" +
                    "    _ _ _\n" +
                    "_ _ _ _ _ _ O\n" +
                    "_ _ _ _ _ O _\n" +
                    "_ _ O _ _ _ _\n" +
                    "    _ _ O\n" +
                    "    O O _\n" +
                    "Score: 7Game Over! \n" +
                    "    O _ _\n" +
                    "    _ _ _\n" +
                    "_ _ _ _ _ _ O\n" +
                    "_ _ _ _ _ O _\n" +
                    "_ _ O _ _ _ _\n" +
                    "    _ _ O\n" +
                    "    O O _\n" +
                    "Score: 7",
            this.stringBuilder.toString());

    //Test when game is not over; start of game
    this.setUp();
    this.initializeController("");
    this.controller3x3Append.playGame();
    assertFalse(this.model3x3.isGameOver());

  }

  //Test when user inputs an invalid input (e.g. a string)
  @Test
  void invalidInput() {

    //when user inputs invalid input as first input
    this.initializeController("abc");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage +
                    "Invalid input, try again: ",
            this.stringBuilder.toString());

    //when user inputs invalid input after moving
    this.setUp();
    this.initializeController("4 2 4 4 \n T");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage + "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O _ _ O O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n" +
                    "Score: 31" +
                    "Invalid input, try again: ",
            this.stringBuilder.toString());

    //when user inputs invalid input in middle of move
    this.setUp();
    this.initializeController("4 \n 2 a ");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage +
                    "Invalid input, try again: ",
            this.stringBuilder.toString());

    //when user inputs just empty space
    this.setUp();
    this.initializeController(" \n");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage +
                    "Invalid input, line must have at least one character.\n" +
                    " Try again:",
            this.stringBuilder.toString());

    //when user inputs invalid empty input in middle of move
    this.setUp();
    this.initializeController("4 \n 2 \n \n");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage +
                    "Invalid input, line must have at least one character.\n" +
                    " Try again:",
            this.stringBuilder.toString());

    //when user inputs just single empty line
    this.setUp();
    this.initializeController(" ");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage +
                    "Invalid input, line must have at least one character.\n" +
                    " Try again:",
            this.stringBuilder.toString());

    //Test triangle
    this.setUp();
    this.initializeController(" ");
    this.triangleController.playGame();
    assertEquals(this.startingTriangleBoard + "\nScore: " + "14\n" +
            this.startMessage + "Invalid input, line must have at least one character.\n" +
            " Try again:", this.triangleStringBuilder.toString());

    this.setUp();
    this.initializeController("4 \n \n");
    this.triangleController.playGame();
    assertEquals(this.startingTriangleBoard + "\nScore: " + "14\n" +
            this.startMessage + "Invalid input, line must have at least one character.\n" +
            " Try again:", this.triangleStringBuilder.toString());


  }

  //Test when user inputs an illegal move
  @Test
  void testIllegalMove() {

    //Test illegal move in game board that not 2 spaces apart
    this.initializeController("4 2 4 5");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage +
                    "Invalid move. Play again. \n" +
                    this.starting3x3board + "\nScore: " + "32",
            this.stringBuilder.toString());

    //Test illegal move in game board that is not horizontal/vertical
    this.initializeController("3 2 4 6");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage +
                    "Invalid move. Play again. \n" +
                    this.starting3x3board + "\nScore: " + "32",
            this.stringBuilder.toString());

    //Test illegal move in game board that does not have
    //empty to cell position
    this.initializeController("4 1 4 3");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage +
                    "Invalid move. Play again. \n" +
                    this.starting3x3board + "\nScore: " + "32",
            this.stringBuilder.toString());

    //Test illegal move that is outside of game board
    this.initializeController("5 50 3 6");
    this.controller3x3Append.playGame();
    assertEquals(this.starting3x3board + "\nScore: " + "32"
                    + "\n" + this.startMessage +
                    "Invalid move. Play again. \n" +
                    this.starting3x3board + "\nScore: " + "32",
            this.stringBuilder.toString());

    //Test triangle
    this.setUp();
    this.initializeController("5 50 4 5");
    this.triangleController.playGame();
    assertEquals(this.startingTriangleBoard + "\nScore: " + "14\n" +
            this.startMessage + "Invalid move. Play again. \n" +
            this.startingTriangleBoard + "\nScore: " + "14", this.triangleStringBuilder.toString());
  }
}
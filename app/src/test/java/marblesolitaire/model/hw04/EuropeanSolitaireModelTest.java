package marblesolitaire.model.hw04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import marblesolitaire.model.hw02.EnglishSolitaireModel;
import marblesolitaire.model.hw02.MarbleSolitaireModelState;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EuropeanSolitaireModelTest {

  EuropeanSolitaireModel model3x3;
  EuropeanSolitaireModel model3x3NotInit;

  EuropeanSolitaireModel model5x5;
  EuropeanSolitaireModel model5x5NotInit;

  /**
   * Reset test variables
   */
  @BeforeEach
  public void reset() {

    model3x3 = new EuropeanSolitaireModel();
    model3x3NotInit = new EuropeanSolitaireModel(true, 3, 3, 3);

    model5x5 = new EuropeanSolitaireModel(5);
    model5x5NotInit = new EuropeanSolitaireModel(true, 5, 6, 6);
  }

  /**
   * Test 2nd constructor exceptions
   */
  @Test
  void constructor2() {

    //test top-left invalid zone
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(0, 0));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(0, 1));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(1, 0));

    //test top-right invalid zone
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(0, 5));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(0, 6));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(1, 6));

    //test bottom-left invalid zone
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(5, 0));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(6, 0));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(6, 1));

    //test bottom-right invalid zone
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(5, 6));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(6, 5));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(6, 6));

    //test out-of-bounds
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(10, 10));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(3, 7));
  }

  /**
   * Test third constructor exceptions
   */
  @Test
  void constructor3() {

    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(-1));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(0));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(2));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(12));

  }

  /**
   * Test fourth constructor exceptions
   */
  @Test
  void constructor4() {

    //test invalid arm thickness
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(-1, 3, 3));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(0, 5, 5));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(2, 3, 3));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(12, 5, 5));

    //test top-left invalid zone
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(3, 0, 0));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(5, 0, 1));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(1, 1, 0));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(10, 1, 1));

    //test top-right invalid zone
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(3, 0, 5));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(3, 0, 6));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(3, 1, 6));

    //test bottom-left invalid zone
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(3, 5, 0));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(3, 6, 0));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(3, 6, 1));

    //test bottom-right invalid zone
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(3, 5, 6));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(3, 6, 5));
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(3, 6, 6));

    //test out-of-bounds
    assertThrows(IllegalArgumentException.class, () -> new EuropeanSolitaireModel(3, 3, 7));

  }


  /**
   * Test move method
   */
  @Test
  void move() {

    this.reset();

    //test starting cell exception
    assertThrows(IllegalArgumentException.class, () -> model3x3.move(0, 0, 3, 3));
    assertThrows(IllegalArgumentException.class, () -> model3x3.move(6, 2, 0, 0));

    //test ending cell exception
    assertThrows(IllegalArgumentException.class, () -> model3x3.move(3, 3, 7, 6));
    assertThrows(IllegalArgumentException.class, () -> model3x3.move(2, 4, 1, 5));

    //test ending cell position not empty exception
    assertThrows(IllegalArgumentException.class, () -> model3x3.move(3, 6, 3, 4));
    assertThrows(IllegalArgumentException.class, () -> model5x5.move(0, 6, 2, 6));

    //test middle cell empty exception
    assertThrows(IllegalArgumentException.class, () -> model3x3.move(3, 4, 3, 2));

    //test move not two spaces exception
    this.model3x3.move(3, 5, 3, 3);
    assertThrows(IllegalArgumentException.class, () -> model3x3.move(3, 2, 3, 5));
    assertThrows(IllegalArgumentException.class, () -> model3x3.move(4, 6, 3, 4));

    //test method
    this.reset();

    assertEquals(this.model3x3.getSlotAt(3, 3), MarbleSolitaireModelState.SlotState.Empty);
    this.model3x3.move(3, 5, 3, 3);
    assertEquals(this.model3x3.getSlotAt(3, 3), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model3x3.getSlotAt(3, 4), MarbleSolitaireModelState.SlotState.Empty);

    assertEquals(this.model3x3.getSlotAt(3, 4), MarbleSolitaireModelState.SlotState.Empty);
    this.model3x3.move(5, 4, 3, 4);
    assertEquals(this.model3x3.getSlotAt(3, 4), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model3x3.getSlotAt(4, 4), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model3x3.getSlotAt(5, 4), MarbleSolitaireModelState.SlotState.Empty);

    assertEquals(this.model5x5.getSlotAt(8, 6), MarbleSolitaireModelState.SlotState.Marble);
    this.model5x5.move(8, 6, 6, 6);
    assertEquals(this.model5x5.getSlotAt(8, 6), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model5x5.getSlotAt(7, 6), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model5x5.getSlotAt(6, 6), MarbleSolitaireModelState.SlotState.Marble);
  }

  /**
   * Test isGameOver method
   */
  @Test
  void isGameOver() {

    assertFalse(this.model3x3.isGameOver());

    this.model3x3.move(3, 5, 3, 3);
    this.model3x3.move(3, 2, 3, 4);
    this.model3x3.move(3, 0, 3, 2);
    this.model3x3.move(5, 3, 3, 3);
    this.model3x3.move(3, 3, 3, 5);
    this.model3x3.move(3, 6, 3, 4);
    this.model3x3.move(1, 3, 3, 3);
    this.model3x3.move(3, 3, 3, 5);
    this.model3x3.move(5, 4, 3, 4);
    this.model3x3.move(4, 6, 4, 4);
    this.model3x3.move(3, 4, 5, 4);
    this.model3x3.move(6, 4, 4, 4);
    this.model3x3.move(2, 5, 2, 3);
    this.model3x3.move(2, 2, 2, 4);
    this.model3x3.move(1, 4, 3, 4);
    this.model3x3.move(4, 2, 2, 2);
    this.model3x3.move(2, 1, 2, 3);
    this.model3x3.move(0, 2, 2, 2);
    this.model3x3.move(2, 3, 2, 1);
    this.model3x3.move(2, 0, 2, 2);
    this.model3x3.move(4, 0, 4, 2);
    this.model3x3.move(3, 4, 5, 4);
    this.model3x3.move(5, 2, 3, 2);
    this.model3x3.move(2, 2, 4, 2);
    this.model3x3.move(0, 4, 0, 2);
    this.model3x3.move(6, 2, 6, 4);
    this.model3x3.move(6, 4, 4, 4);

    assertTrue(this.model3x3.isGameOver());

  }

  /**
   * Test getBoardSize method
   */
  @Test
  void getBoardSize() {

    assertEquals(this.model3x3.getBoardSize(), 7);
    assertEquals(this.model5x5.getBoardSize(), 13);

  }

  /**
   * Test getSlotAt method
   */
  @Test
  void getSlotAt() {

    //test out-of-bounds slot exception
    assertThrows(IllegalArgumentException.class, () -> model3x3.getSlotAt(9, 9));
    assertThrows(IllegalArgumentException.class, () -> model3x3.getSlotAt(-1, 2));
    assertThrows(IllegalArgumentException.class, () -> model5x5.getSlotAt(15, 4));
    assertThrows(IllegalArgumentException.class, () -> model5x5.getSlotAt(3, 23));

    //test inside cross
    assertEquals(this.model3x3.getSlotAt(3, 3), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model3x3.getSlotAt(3, 4), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model3x3.getSlotAt(0, 0), MarbleSolitaireModelState.SlotState.Invalid);

    assertEquals(this.model5x5.getSlotAt(6, 6), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model5x5.getSlotAt(5, 11), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model5x5.getSlotAt(11, 11), MarbleSolitaireModelState.SlotState.Invalid);

    //test diagonal edges
    assertEquals(this.model3x3.getSlotAt(0, 1), MarbleSolitaireModelState.SlotState.Invalid);
    assertEquals(this.model3x3.getSlotAt(1, 1), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model3x3.getSlotAt(1, 5), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model3x3.getSlotAt(1, 6), MarbleSolitaireModelState.SlotState.Invalid);
    assertEquals(this.model3x3.getSlotAt(5, 1), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model3x3.getSlotAt(5, 5), MarbleSolitaireModelState.SlotState.Marble);

    assertEquals(this.model5x5.getSlotAt(2, 2), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model5x5.getSlotAt(1, 1), MarbleSolitaireModelState.SlotState.Invalid);
    assertEquals(this.model5x5.getSlotAt(2, 10), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model5x5.getSlotAt(10, 2), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model5x5.getSlotAt(10, 12), MarbleSolitaireModelState.SlotState.Invalid);
    assertEquals(this.model5x5.getSlotAt(10, 10), MarbleSolitaireModelState.SlotState.Marble);

  }

  /**
   * Test getScore method
   */
  @Test
  void getScore() {

    assertEquals(this.model3x3.getScore(), 36);
    this.model3x3.move(3, 5, 3, 3);
    assertEquals(this.model3x3.getScore(), 35);
    this.model3x3.move(3, 2, 3, 4);
    assertEquals(this.model3x3.getScore(), 34);

    assertEquals(this.model5x5.getScore(), 128);
    this.model5x5.move(6, 8, 6, 6);
    assertEquals(this.model5x5.getScore(), 127);
    this.model5x5.move(6, 5, 6, 7);
    assertEquals(this.model5x5.getScore(), 126);

  }

}
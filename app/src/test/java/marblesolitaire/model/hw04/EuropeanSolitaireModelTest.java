package marblesolitaire.model.hw04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import marblesolitaire.model.hw02.EnglishSolitaireModel;
import marblesolitaire.model.hw02.MarbleSolitaireModelState;

import static org.junit.jupiter.api.Assertions.*;

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

    assertEquals(this.model3x3.getSlotAt(3,4), MarbleSolitaireModelState.SlotState.Empty);
    this.model3x3.move(5, 4, 3, 4);
    assertEquals(this.model3x3.getSlotAt(3,4), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model3x3.getSlotAt(4,4), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model3x3.getSlotAt(5,4), MarbleSolitaireModelState.SlotState.Empty);

    assertEquals(this.model5x5.getSlotAt(8,6), MarbleSolitaireModelState.SlotState.Marble);
    this.model5x5.move(8, 6, 6, 6);
    assertEquals(this.model5x5.getSlotAt(8,6), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model5x5.getSlotAt(7,6), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model5x5.getSlotAt(6,6), MarbleSolitaireModelState.SlotState.Marble);
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

    //test method
    assertEquals(this.model3x3.getSlotAt(3, 3), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model3x3.getSlotAt(3, 4), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model3x3.getSlotAt(0, 0), MarbleSolitaireModelState.SlotState.Invalid);

    assertEquals(this.model5x5.getSlotAt(6, 6), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model5x5.getSlotAt(5, 11), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model5x5.getSlotAt(11, 11), MarbleSolitaireModelState.SlotState.Invalid);

  }

}
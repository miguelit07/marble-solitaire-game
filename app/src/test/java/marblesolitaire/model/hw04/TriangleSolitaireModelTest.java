package marblesolitaire.model.hw04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import marblesolitaire.model.hw02.MarbleSolitaireModelState;
import marblesolitaire.view.TriangleSolitaireTextView;

import static org.junit.jupiter.api.Assertions.*;

class TriangleSolitaireModelTest {

  TriangleSolitaireModel model5Length;
  TriangleSolitaireModel model7Length;

  @BeforeEach
  public void reset() {
    model5Length = new TriangleSolitaireModel();
    model7Length = new TriangleSolitaireModel(7);
  }

  /**
   * Test 2nd constructor exceptions
   */
  @Test
  void constructor2() {
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(0));
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(-1));
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(-2));
  }

  /**
   * Test 3rd constructor exceptions
   */
  @Test
  void constructor3() {
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(0, 0));
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(1, 2));
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(3, 0));
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(0, 5));
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(3, 8));
  }

  /**
   * Test 4th constructor exceptions
   */
  @Test
  void constructor4() {

    //test invalid arm thickness
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(-1, 0, 4));
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(-3, 0, 6));
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(0, 5, 5));

    //test invalid empty slot
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(5, 0, 5));
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(7, 3, 1));
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(-4, 3, 6));

  }


  /**
   * Test move method
   */
  @Test
  void move() {

    this.reset();

    //test starting cell exception
    assertThrows(IllegalArgumentException.class, () -> model5Length.move(0, 0, 3, 3));
    assertThrows(IllegalArgumentException.class, () -> model5Length.move(6, 2, 0, 0));

    //test ending cell exception
    assertThrows(IllegalArgumentException.class, () -> model5Length.move(3, 3, 7, 6));
    assertThrows(IllegalArgumentException.class, () -> model5Length.move(2, 4, 1, 5));

    //test ending cell position not empty exception
    assertThrows(IllegalArgumentException.class, () -> model5Length.move(1, 3, 1, 5));
    assertThrows(IllegalArgumentException.class, () -> model5Length.move(2, 4, 2, 6));

    //test middle cell empty exception
    assertThrows(IllegalArgumentException.class, () -> model5Length.move(3, 4, 3, 2));


    //test method
    this.reset();

    assertEquals(this.model5Length.getSlotAt(0, 4), MarbleSolitaireModelState.SlotState.Empty);
    this.model5Length.move(2, 2, 0, 4);
    assertEquals(this.model5Length.getSlotAt(0, 4), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model5Length.getSlotAt(2, 2), MarbleSolitaireModelState.SlotState.Empty);

    assertEquals(this.model5Length.getSlotAt(1, 3), MarbleSolitaireModelState.SlotState.Empty);
    this.model5Length.move(3, 5, 1, 3);
    assertEquals(this.model5Length.getSlotAt(1, 3), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model5Length.getSlotAt(2, 4), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model5Length.getSlotAt(3, 5), MarbleSolitaireModelState.SlotState.Empty);

    assertEquals(this.model7Length.getSlotAt(0, 6), MarbleSolitaireModelState.SlotState.Empty);
    this.model7Length.move(2, 8, 0, 6);
    assertEquals(this.model7Length.getSlotAt(2, 8), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model7Length.getSlotAt(1, 7), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model7Length.getSlotAt(0, 6), MarbleSolitaireModelState.SlotState.Marble);
  }

  /**
   * Test getScore method
   */
  @Test
  void getScore() {

    assertEquals(this.model5Length.getScore(), 14);
    this.model5Length.move(2, 2, 0, 4);
    assertEquals(this.model5Length.getScore(), 13);
    this.model5Length.move(3, 5, 1, 3);
    assertEquals(this.model5Length.getScore(), 12);

    assertEquals(this.model7Length.getScore(), 27);
    this.model7Length.move(2, 8, 0, 6);
    assertEquals(this.model7Length.getScore(), 26);
    this.model7Length.move(4, 10, 2, 8);
    assertEquals(this.model7Length.getScore(), 25);
  }

  /**
   * Test isGameOver method
   */
  @Test
  void isGameOver() {

    assertFalse(this.model5Length.isGameOver());

    this.model5Length.move(2, 2, 0, 4);
    this.model5Length.move(3, 5, 1, 3);
    this.model5Length.move(4, 0, 2, 2);
    this.model5Length.move(1, 3, 3, 1);
    this.model5Length.move(4, 4, 2, 2);
    this.model5Length.move(4, 8, 4, 4);
    this.model5Length.move(3, 1, 1, 3);
    this.model5Length.move(0, 4, 2, 2);
    this.model5Length.move(2, 6, 0, 4);
    this.model5Length.move(4, 2, 4, 6);

    assertTrue(this.model5Length.isGameOver());

  }

  /**
   * Test getBoardSize method
   */
  @Test
  void getBoardSize() {

    assertEquals(this.model5Length.getBoardSize(), 9);
    assertEquals(this.model7Length.getBoardSize(), 13);

  }

  /**
   * Test getSlotAt method
   */
  @Test
  void getSlotAt() {

    //test out-of-bounds slot exception
    assertThrows(IllegalArgumentException.class, () -> model5Length.getSlotAt(9, 9));
    assertThrows(IllegalArgumentException.class, () -> model5Length.getSlotAt(-1, 2));
    assertThrows(IllegalArgumentException.class, () -> model7Length.getSlotAt(15, 4));
    assertThrows(IllegalArgumentException.class, () -> model7Length.getSlotAt(3, 23));

    assertEquals(this.model5Length.getSlotAt(0, 4), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model5Length.getSlotAt(3, 5), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model5Length.getSlotAt(3, 4), MarbleSolitaireModelState.SlotState.Invalid);

    assertEquals(this.model7Length.getSlotAt(0, 6), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(this.model7Length.getSlotAt(4, 4), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(this.model7Length.getSlotAt(5, 6), MarbleSolitaireModelState.SlotState.Invalid);
  }
}
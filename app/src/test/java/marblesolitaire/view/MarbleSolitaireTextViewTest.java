package marblesolitaire.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import marblesolitaire.model.hw02.EnglishSolitaireModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MarbleSolitaireTextViewTest {

  EnglishSolitaireModel model3x3;
  EnglishSolitaireModel model5x5;

  MarbleSolitaireView view3x3;
  MarbleSolitaireView view5x5;
  MarbleSolitaireTextView view3x3Text;
  StringBuilder sb;
  MarbleSolitaireTextView view3x3TextAppend;


  @BeforeEach
  void reset() {
    model3x3 = new EnglishSolitaireModel();
    model5x5 = new EnglishSolitaireModel(5);

    view3x3 = new MarbleSolitaireTextView(model3x3);
    view5x5 = new MarbleSolitaireTextView(model5x5);

    view3x3Text = new MarbleSolitaireTextView(model3x3);
    sb = new StringBuilder();
    view3x3TextAppend = new MarbleSolitaireTextView(model3x3, sb);
  }


  /**
   * Test constructor exception
   */
  @Test
  void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new MarbleSolitaireTextView(null));
  }

  /**
   * Test toString method
   */
  @Test
  void testToString() {
    assertEquals(view3x3.toString(), "    O O O\n" +
            "    O O O\n" +
            "O O O O O O O\n" +
            "O O O _ O O O\n" +
            "O O O O O O O\n" +
            "    O O O\n" +
            "    O O O");

    assertEquals(view5x5.toString(), "        O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O O O O O O _ O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O");
  }

  @Test
  void renderBoard() throws IOException {
    assertEquals(this.view3x3TextAppend.destination.toString(), "");
    this.view3x3TextAppend.renderBoard();
    assertEquals(this.view3x3TextAppend.destination.toString(), this.view3x3Text.toString());

  }

  @Test
  void renderMessage() throws IOException {
    assertEquals(this.view3x3TextAppend.destination.toString(), "");
    this.view3x3TextAppend.renderMessage("Game is over");
    assertEquals(this.view3x3TextAppend.destination.toString(), "Game is over");

  }
}
package marblesolitaire.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import marblesolitaire.model.hw02.EnglishSolitaireModel;
import marblesolitaire.model.hw04.TriangleSolitaireModel;

import static org.junit.jupiter.api.Assertions.*;

class TriangleSolitaireTextViewTest {

  TriangleSolitaireModel model5Length;

  TriangleSolitaireModel model7Length;

  TriangleSolitaireTextView triangleView;

  TriangleSolitaireTextView triangleView7x7;


  StringBuilder sb;

  TriangleSolitaireTextView viewModel5TextAppend;

  @BeforeEach
  void setUp() {
    model5Length = new TriangleSolitaireModel();

    model7Length = new TriangleSolitaireModel(7);

    triangleView = new TriangleSolitaireTextView(model5Length);

    triangleView = new TriangleSolitaireTextView(model5Length);


    triangleView7x7 = new TriangleSolitaireTextView(model7Length);

    sb = new StringBuilder();

    viewModel5TextAppend = new TriangleSolitaireTextView(model5Length, sb);
  }

  /**
   * Test constructor exception
   */
  @Test
  void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new MarbleSolitaireTextView(null));
  }

  @Test
  void testToString() {
    assertEquals(triangleView.toString(), "    _\n" +
            "   O O\n" +
            "  O O O\n" +
            " O O O O\n" +
            "O O O O O");

    assertEquals(triangleView7x7.toString(), "      _\n" +
            "     O O\n" +
            "    O O O\n" +
            "   O O O O\n" +
            "  O O O O O\n" +
            " O O O O O O\n" +
            "O O O O O O O");
  }

  @Test
  void renderBoard() throws IOException {

    assertEquals(this.viewModel5TextAppend.destination.toString(), "");
    this.viewModel5TextAppend.renderBoard();
    assertEquals(this.viewModel5TextAppend.destination.toString(), this.triangleView.toString());

  }

  @Test
  void renderMessage() throws IOException {

    assertEquals(this.viewModel5TextAppend.destination.toString(), "");
    this.viewModel5TextAppend.renderMessage("Game is over");
    assertEquals(this.viewModel5TextAppend.destination.toString(), "Game is over");

  }
}
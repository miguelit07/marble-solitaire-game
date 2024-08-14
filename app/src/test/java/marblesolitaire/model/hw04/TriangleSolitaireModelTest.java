package marblesolitaire.model.hw04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(-1, 0, 4));
    assertThrows(IllegalArgumentException.class, () -> new TriangleSolitaireModel(4, 0, 0));
  }
}
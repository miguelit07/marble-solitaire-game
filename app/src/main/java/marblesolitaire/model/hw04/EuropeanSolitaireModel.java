package marblesolitaire.model.hw04;

/**
 * Represents a European model of solitaire game
 */
public class EuropeanSolitaireModel extends AbstractSolitaireModel {

  /**
   * Default constructor that creates an octagonal board
   * whose sides have length 3, with the empty slot in
   * the center of the board
   */
  public EuropeanSolitaireModel() {
    super();
  }

  /**
   * Constructor with a single parameter arm thickness (a.k.a. side length)
   * that creates a game with the specified side length,
   * and the empty slot in the center of the board.
   *
   * @param armThickness the length of the board size in int
   */
  public EuropeanSolitaireModel(int armThickness) {
    super(armThickness);
  }

  /**
   * Constructor with two parameters (row, col) to specify
   * the initial position of the empty slot, in a board
   * of default size 3
   *
   * @param row the row location of the empty slot
   * @param col the col location of the empty slot
   */
  public EuropeanSolitaireModel(int row, int col) {
    super(row, col);
  }

  /**
   * Constructor with three parameters (arm thickness/sidelength, row col),
   * to specify the size of the board and the initial position
   * of the empty slot
   *
   * @param armThickness the length of the board size as an int
   * @param row          the row location of the empty slot
   * @param col          the col location of the empty slot
   */
  public EuropeanSolitaireModel(int armThickness, int row, int col) {
    super(armThickness, row, col);
  }

  /**
   * Constructor for testing initBoard
   *
   * @param notInit      do not initialize board
   * @param armThickness determines the armThickness of the game board
   * @param row          row of empty slot
   * @param col          column of empty slot
   */
  public EuropeanSolitaireModel(boolean notInit, int armThickness, int row, int col) {
    super(notInit, armThickness, row, col);
  }

  /**
   * determines if a given slot position is a valid state
   * a valid slot is in the game board, slots out of bounds
   * are not valid
   *
   * @param row the row of position
   * @param col the column of position
   * @return if the slot is valid
   */
  protected boolean isValid(int row, int col) {
    int invalidPos = (size - armThickness) / 2;

    //call helper method to determine the slot state
    //when not in cross-section (diagonal zones)
    if (this.isValidDiagonalZones(row, col, invalidPos)) {
      return true;
    }

    //Determine valid slots in Cross-section
    return ((row >= invalidPos && row < size - invalidPos) || (col >= invalidPos && col < size - invalidPos))
            && (row >= 0 && row < size && col >= 0 && col < size);
  }

  /**
   * Helper method for isValid, determines which marbles to print in
   * diagonal zones of the board (top-left, top-right, bottom-left
   * and bottom-right)
   *
   * @param row        the row of position
   * @param col        the col of position
   * @param invalidPos the invalid sections of the game board
   * @return if slot is valid
   */
  private boolean isValidDiagonalZones(int row, int col, int invalidPos) {
    //Top-left invalid zone
    if (row < invalidPos && col < invalidPos) {
      return (row + col > invalidPos - 1)
              && row > 0
              && col > 0;
    }
    //Top-right invalid zone
    else if (row < invalidPos && col > (size - armThickness)) {
      return (row >= col - (size - armThickness));
    }
    //Bottom-left invalid zone
    else if (row > (size - armThickness) && col < invalidPos) {
      return (row - (size - armThickness + 1) < col);
    }
    //Bottom-right invalid zone
    else if (row > (size - armThickness) && col > (size - armThickness)) {
      return (row + col < 2 * size - armThickness);
    } else {
      return false;
    }
  }
}

package marblesolitaire.model.hw02;

import marblesolitaire.model.hw04.AbstractSolitaireModel;

/**
 * Represents an english model board
 */
public class EnglishSolitaireModel extends AbstractSolitaireModel {

  /**
   * Initialize the game board as shown in example
   * (arm thickness 3 with the empty slot at the center)
   */
  public EnglishSolitaireModel() {
    super();
  }

  /**
   * It should initialize the game board so that the arm thickness is 3 and the empty slot
   * is at the position (sRow, sCol). If this specified position is invalid, it should throw
   * an IllegalArgumentException with a message "Invalid empty cell position (r,c)" with and
   * replaced with the row and column passed to it.
   *
   * @param sRow row of empty slot
   * @param sCol column of empty slot
   * @throws IllegalArgumentException "Invalid empty cell position (r,c)"
   */
  public EnglishSolitaireModel(int sRow, int sCol) {
    super(sRow, sCol);
  }

  /**
   * Initializes game board with empty slot at center based on given armThickness
   *
   * @param armThickness determines the armThickness of game board
   * @throws IllegalArgumentException "Arm thickness is not a positive number"
   */
  public EnglishSolitaireModel(int armThickness) {
    super(armThickness);
  }

  /**
   * Initializes game board based off given arm thickness, row and column of the empty slot
   *
   * @param armThickness determines the armThickness of the game board
   * @param row          row of empty slot
   * @param col          column of empty slot
   * @throws IllegalArgumentException if the arm thickness is not a positive odd number,
   *                                  or the empty cell position is invalid.
   *                                  if row and col are not a valid cell position
   */
  public EnglishSolitaireModel(int armThickness, int row, int col) {
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
  public EnglishSolitaireModel(boolean notInit, int armThickness, int row, int col) {
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
    return ((row >= invalidPos && row < size - invalidPos) || (col >= invalidPos && col < size - invalidPos))
            && (row >= 0 && row < size && col >= 0 && col < size);
  }
}


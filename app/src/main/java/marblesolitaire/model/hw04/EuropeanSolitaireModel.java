package marblesolitaire.model.hw04;

import java.io.StringReader;

import marblesolitaire.controller.MarbleSolitaireController;
import marblesolitaire.controller.MarbleSolitaireControllerImpl;
import marblesolitaire.model.hw02.MarbleSolitaireModel;
import marblesolitaire.view.MarbleSolitaireTextView;

public class EuropeanSolitaireModel implements MarbleSolitaireModel {
  private final int armThickness;
  private final int size;
  protected SlotState[][] board;

  /**
   * Default constructor that creates an octagonal board
   * whose sides have length 3, with the empty slot in
   * the center of the board
   */
  public EuropeanSolitaireModel() {
    this(3, 3, 3);
  }

  /**
   * Constructor with a single parameter arm thickness (a.k.a. side length)
   * that creates a game with the specified side length,
   * and the empty slot in the center of the board.
   *
   * @param armThickness the length of the board size in int
   */
  public EuropeanSolitaireModel(int armThickness) {
    this(armThickness, ((armThickness * 3) - 2) / 2, ((armThickness * 3) - 2) / 2);

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
    this(3, row, col);
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
    if ((armThickness < 0) || (armThickness % 2 == 0)) {
      throw new IllegalArgumentException("Arm thickness is not a positive odd number");
    }

    this.armThickness = armThickness;
    this.size = 3 * armThickness - 2;
    this.board = new SlotState[this.size][this.size];
    this.initBoard();

    if (!(isValid(row, col))) {
      throw new IllegalArgumentException("Invalid empty cell position" + row + "," + col + ")");
    }

    this.board[row][col] = SlotState.Empty;
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
    this.armThickness = armThickness;
    this.size = 3 * armThickness - 2;
    this.board = new SlotState[this.size][this.size];
    this.board[row][col] = SlotState.Empty;

  }

  /**
   * Initializes board by looping through board and determining slot state
   */
  private void initBoard() {
    for (int i = 0; i < this.getBoardSize(); i++) {
      for (int j = 0; j < this.getBoardSize(); j++) {
        if (isValid(i, j)) {
          this.board[i][j] = SlotState.Marble;
        } else {
          this.board[i][j] = SlotState.Invalid;
        }
      }
    }
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
  private boolean isValid(int row, int col) {
    int invalidPos = (size - armThickness) / 2;

    if (this.isValidDiagonalZones(row, col, invalidPos)) {
      return true;
    }

    //Cross-section
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

  /**
   * To move a marble from cell to another empty cell
   *
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0)
   * @param fromCol the column number of the position to be moved from
   *                starts at 0)
   * @param toRow   the row number of the position to be moved to
   *                (starts at 0)
   * @param toCol   the column number of the position to be moved to
   *                (starts at 0)
   * @throws IllegalArgumentException if invalid starting/ending position
   * @throws IllegalArgumentException if ending position is empty
   * @throws IllegalArgumentException if move is not exactly two positions away from starting location
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    if (!isValid(fromRow, fromCol)) {
      throw new IllegalArgumentException("Invalid starting cell position" + fromRow + "," + fromCol);
    }
    if (!isValid(toRow, toCol)) {
      throw new IllegalArgumentException("Invalid ending cell position" + toRow + "," + toCol);
    }
    if (this.getSlotAt(toRow, toCol) != SlotState.Empty) {
      throw new IllegalArgumentException("Ending cell position is not empty " + toRow + "," + toCol);
    }

    int rowDiff = Math.abs(toRow - fromRow);
    int colDiff = Math.abs(toCol - fromCol);

    if ((rowDiff == 2 && colDiff == 0) || (rowDiff == 0 && colDiff == 2)) {
      int middleRow = (fromRow + toRow) / 2;
      int middleCol = (fromCol + toCol) / 2;

      if (this.board[middleRow][middleCol] == SlotState.Marble) {
        this.board[middleRow][middleCol] = SlotState.Empty;
        this.board[fromRow][fromCol] = SlotState.Empty;
        this.board[toRow][toCol] = SlotState.Marble;

      } else {
        throw new IllegalArgumentException("The middle position is empty " + middleRow + "," + middleCol);
      }
    } else {
      throw new IllegalArgumentException("This move is illegal. Move must be two horizontal or vertical steps");
    }

  }

  /**
   * Determine and return if the game is over or not. Goes through
   * every cell in board and determines if a move can be made
   * if there are no cells that can make a move, then the
   * game is over
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    for (int row = 0; row < this.getBoardSize(); row++) {
      for (int col = 0; col < this.getBoardSize(); col++) {
        if (movePossible(row, col, row - 2, col) ||
                (movePossible(row, col, row + 2, col)) ||
                (movePossible(row, col, row, col - 2)) ||
                (movePossible(row, col, row, col - 2))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Determines if there are moves possible based of given origin row and column
   * and destination row and column. Helper function for isGameOver()
   *
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0)
   * @param fromCol the column number of the position to be moved from
   *                starts at 0)
   * @param toRow   the row number of the position to be moved to
   *                (starts at 0)
   * @param toCol   the column number of the position to be moved to
   *                (starts at 0)
   * @return if there are moves possible from this point
   */
  private boolean movePossible(int fromRow, int fromCol, int toRow, int toCol) {
    //check if ending cell is valid and if it is empty
    if (!isValid(toRow, toCol) || this.getSlotAt(toRow, toCol) != SlotState.Empty) {
      return false;
    }
    //check if starting cell and middle cell are marbles
    if ((Math.abs(fromRow - toRow) == 2 && fromCol == toCol) ||
            (Math.abs(fromCol - toCol) == 2 && fromRow == toRow)) {
      int midRow = (fromRow + toRow) / 2;
      int midCol = (fromCol + toCol) / 2;
      return this.getSlotAt(midRow, midCol) == SlotState.Marble && this.getSlotAt(fromRow, fromCol) == SlotState.Marble;
    }
    return false;
  }

  /**
   * Return the size of this board. The size is roughly the longest dimension of a board
   *
   * @return the size as an integer
   */
  @Override
  public int getBoardSize() {
    return this.size;
  }

  /**
   * Get the state of the slot at a given position on the board.
   *
   * @param row the row of the position sought, starting at 0
   * @param col the column of the position sought, starting at 0
   * @return the state of the slot at the given row and column
   * @throws IllegalArgumentException if the row or the column are beyond
   *                                  the dimensions of the board
   */
  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= this.size || col < 0 || col >= this.size) {
      throw new IllegalArgumentException("Row or column out of bounds");
    }
    return this.board[row][col];
  }

  /**
   * Return the number of marbles currently on the board.
   *
   * @return the number of marbles currently on the board as an int
   */
  @Override
  public int getScore() {
    int startingScore = 0;

    for (int i = 0; i < this.getBoardSize(); i++) {
      for (int j = 0; j < this.getBoardSize(); j++) {
        if (this.board[i][j] == SlotState.Marble) {
          startingScore++;
        }
      }
    }
    return startingScore;
  }

  public static void main(String args[]) {
    EuropeanSolitaireModel europeanModel = new EuropeanSolitaireModel(5);
    MarbleSolitaireTextView viewEuropean = new MarbleSolitaireTextView(europeanModel);
    System.out.println(viewEuropean);

  }
}

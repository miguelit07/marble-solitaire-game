package marblesolitaire.model.hw04;

import marblesolitaire.model.hw02.MarbleSolitaireModel;

public abstract class AbstractSolitaireModel implements MarbleSolitaireModel {
  protected final int armThickness;
  protected final int size;
  protected SlotState[][] board;

  /**
   * Default constructor that creates an octagonal board
   * whose sides have length 3, with the empty slot in
   * the center of the board
   */
  public AbstractSolitaireModel() {
    this(3, 3, 3);
  }

  /**
   * Constructor with a single parameter arm thickness (a.k.a. side length)
   * that creates a game with the specified side length,
   * and the empty slot in the center of the board.
   *
   * @param armThickness the length of the board size in int
   */
  public AbstractSolitaireModel(int armThickness) {
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
  public AbstractSolitaireModel(int row, int col) {
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
  public AbstractSolitaireModel(int armThickness, int row, int col) {
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

  abstract void initBoard();

  abstract boolean isValid(int row, int col);

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
  abstract boolean movePossible(int fromRow, int fromCol, int toRow, int toCol);

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
}

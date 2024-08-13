package marblesolitaire.model.hw02;

public class EnglishSolitaireModel implements MarbleSolitaireModel {
  private final int armThickness;
  private final int size;
  protected SlotState[][] board;

  /**
   * Initialize the game board as shown in example
   * (arm thickness 3 with the empty slot at the center)
   */
  public EnglishSolitaireModel() {
    this(3, 3, 3);
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
    this(3, sRow, sCol);
  }

  /**
   * Initializes game board with empty slot at center based on given armThickness
   *
   * @param armThickness determines the armThickness of game board
   * @throws IllegalArgumentException "Arm thickness is not a positive number"
   */
  public EnglishSolitaireModel(int armThickness) {
    this(armThickness, ((armThickness * 3) - 2) / 2, ((armThickness * 3) - 2) / 2);
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
  public EnglishSolitaireModel(boolean notInit, int armThickness, int row, int col) {
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
    return ((row >= invalidPos && row < size - invalidPos) || (col >= invalidPos && col < size - invalidPos))
            && (row >= 0 && row < size && col >= 0 && col < size);
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
}


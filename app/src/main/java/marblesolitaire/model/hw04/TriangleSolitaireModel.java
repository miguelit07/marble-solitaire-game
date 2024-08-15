package marblesolitaire.model.hw04;

/**
 * Represents triangle model of solitaire game
 */
public class TriangleSolitaireModel extends AbstractSolitaireModel {

  /**
   * Default constructor that creates a 5-row game with the empty slot
   * at (0,0)
   */
  public TriangleSolitaireModel() {
    this(5, 0, 4);
  }

  /**
   * A constructor with a single parameter (dimensions) that creates a game with the specified dimension
   * (number of slots in the bottom-most row) and the empty slot at (0,0).
   *
   * @param dimensions the number of slots in the bottom-most row
   * @throws IllegalArgumentException if dimensions is not a positive number (throws in super constructor)
   */
  public TriangleSolitaireModel(int dimensions) {
    this(dimensions, 0, dimensions - 1);
  }

  /**
   * Constructor with two parameters (row,col) that creates a 5-row game
   * with the empty slot at the specified position.
   *
   * @param row the row of the empty slot
   * @param col the col of the empty slot
   * @throws IllegalArgumentException if empty slot position is invalid (throws in super constructor)
   */
  public TriangleSolitaireModel(int row, int col) {
    this(5, row, col);
  }

  /**
   * A constructor with three parameters (dimensions,row,col) that creates a game with the specified dimension
   * and an empty slot at the specified row and column.
   *
   * @param dimensions the number of slots in the bottom-most row
   * @param row        the row of the empty slot
   * @param col        the col of the empty slot
   * @throws IllegalArgumentException if empty slot position is invalid
   *                                  or if dimensions is non-positive (throws in super constructor)
   */
  public TriangleSolitaireModel(int dimensions, int row, int col) {
    super(row, col, dimensions, ((dimensions * 2) - 1));
  }

  /**
   * Initializes board by looping through board and determining slot state
   */
  protected void initBoard() {
    //number of rows is for triangles is based on armThickness (dimensions)
    for (int i = 0; i < this.armThickness; i++) {
      //number of col is now the horizontal size of board
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
   * Determines if a given slot position is a valid state
   * a valid slot is in the game board, slots out of bounds
   * are not valid
   *
   * @param row the row of position
   * @param col the column of position
   * @return if the slot is valid
   */
  @Override
  protected boolean isValid(int row, int col) {
    //Calculates distance of current row to bottom row
    int distanceFromRow = Math.abs(row - this.armThickness) - 1;

    //If the distance from the bottom row is even
    if (distanceFromRow % 2 == 0) {
      return (col % 2 == 0)
              && (col >= distanceFromRow)
              && (col < size - distanceFromRow);
    }
    //If the distance from the bottom row is odd
    if (distanceFromRow % 2 == 1) {
      return (col % 2 == 1)
              && (col >= distanceFromRow)
              && (col < size - distanceFromRow);
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
    //Calculate slots between from and to slots
    int middleRow = Math.abs(fromRow + toRow) / 2;
    int middleCol = Math.abs(fromCol + toCol) / 2;

    //If middle slot is a marble
    if (this.board[middleRow][middleCol] == SlotState.Marble) {
      //Change marbles from move
      this.board[middleRow][middleCol] = SlotState.Empty;
      this.board[fromRow][fromCol] = SlotState.Empty;
      this.board[toRow][toCol] = SlotState.Marble;

    } else {
      throw new IllegalArgumentException("The middle position is empty " + middleRow + "," + middleCol);
    }
  }

  /**
   * Return the number of marbles currently on the board.
   *
   * @return the number of marbles currently on the board as an int
   */
  @Override
  public int getScore() {
    int startingScore = 0;

    for (int i = 0; i < this.armThickness; i++) {
      for (int j = 0; j < this.getBoardSize(); j++) {
        if (this.board[i][j] == SlotState.Marble) {
          startingScore++;
        }
      }
    }
    return startingScore;
  }

  /**
   * Method to check if current game is over,
   * meaning there are no possible moves left
   *
   * @return if the game is over
   */
  @Override
  public boolean isGameOver() {
    for (int row = 0; row < this.armThickness; row++) {
      for (int col = 0; col < this.getBoardSize(); col++) {
        //Check for a valid move to the right
        if (movePossible(row, col, row, col + 4)) {
          return false;
        }
        //Check for a valid move to the left
        if (movePossible(row, col, row, col - 4)) {
          return false;
        }
        //Check for a valid right diagonal move
        if (movePossible(row, col, row - 2, col + 2)) {
          return false;
        }
        //Check for a valid left diagonal move
        if (movePossible(row, col, row - 2, col - 2)) {
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
  protected boolean movePossible(int fromRow, int fromCol, int toRow, int toCol) {
    //Check if ending cell is valid and if it is empty
    if (!isValid(toRow, toCol) || this.getSlotAt(toRow, toCol) != SlotState.Empty) {
      return false;
    }

    //Check if from slot and middle slot is a marble
    int midRow = Math.abs(fromRow + toRow) / 2;
    int midCol = Math.abs(fromCol + toCol) / 2;
    return this.getSlotAt(midRow, midCol) == SlotState.Marble && this.getSlotAt(fromRow, fromCol) == SlotState.Marble;
  }
}

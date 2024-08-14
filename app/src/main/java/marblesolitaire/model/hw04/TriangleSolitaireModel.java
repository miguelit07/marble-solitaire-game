package marblesolitaire.model.hw04;

import marblesolitaire.view.MarbleSolitaireTextView;
import marblesolitaire.view.TriangleSolitaireTextView;

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
   * @throws IllegalArgumentException if dimensions is not a positive number
   */
  public TriangleSolitaireModel(int dimensions) {
    this(dimensions, 0, dimensions - 1);
    if (dimensions < 1) {
      throw new IllegalArgumentException("Dimensions must be a positive number.");
    }
  }

  /**
   * Constructor with two parameters (row,col) that creates a 5-row game
   * with the empty slot at the specified position.
   *
   * @param row the row of the empty slot
   * @param col the col of the empty slot
   * @throws IllegalArgumentException if empty slot position is invalid
   */
  public TriangleSolitaireModel(int row, int col) {
    this(5, row, col);
    if (!this.isValid(row, col)) {
      throw new IllegalArgumentException("Empty slot position must be valid");
    }
  }

  /**
   * A constructor with three parameters (dimensions,row,col) that creates a game with the specified dimension
   * and an empty slot at the specified row and column.
   *
   * @param dimensions the number of slots in the bottom-most row
   * @param row        the row of the empty slot
   * @param col        the col of the empty slot
   * @throws IllegalArgumentException if empty slot position is invalid
   */
  public TriangleSolitaireModel(int dimensions, int row, int col) {
    super(row, col, dimensions, ((dimensions * 2) - 1));
//    if (!this.isValid(row, col)) {
//      throw new IllegalArgumentException("Empty slot position must be valid");
//    }
    if (dimensions < 1) {
      throw new IllegalArgumentException("Dimensions must be a positive number.");
    }
  }

  /**
   * Initializes board by looping through board and determining slot state
   */
  protected void initBoard() {
    //number of rows is now based on armThickness (dimensions)
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



  @Override
  protected boolean isValid(int row, int col) {
    //Calculates distance of current row to bottom row
    int distanceFromRow = Math.abs(row - this.armThickness) - 1;

    //If the distance from the bottom row is even
    if (distanceFromRow % 2 == 0) {
      //TODO, this is probably unnecessary
//      if (distanceFromRow == this.armThickness) {
//        return col == (size + 1)/ 2;
//      }
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

    int rowDiff = Math.abs(toRow - fromRow);
    int colDiff = Math.abs(toCol - fromCol);

//    if ((rowDiff == 2 && colDiff == 0) || (rowDiff == 0 && colDiff == 2)) {
      int middleRow = Math.abs(fromRow + toRow) / 2;
      int middleCol = Math.abs(fromCol + toCol) / 2;

      if (this.board[middleRow][middleCol] == SlotState.Marble) {
        this.board[middleRow][middleCol] = SlotState.Empty;
        this.board[fromRow][fromCol] = SlotState.Empty;
        this.board[toRow][toCol] = SlotState.Marble;

      } else {
        throw new IllegalArgumentException("The middle position is empty " + middleRow + "," + middleCol);
      }
//    } else {
//      throw new IllegalArgumentException("This move is illegal. Move must be two horizontal or vertical steps");
//    }
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
        //Check for a valid lower right diagonal move
        if (movePossible(row, col, row + 2, col + 4)) {
          return false;
        }
        //Check for a valid upper right diagonal move
        if (movePossible(row, col, row - 2, col + 2)) {
          return false;
        }
        //Check for an upper left diagonal move
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

  public static void main(String[] args) {
//    EuropeanSolitaireModel europeanModel = new EuropeanSolitaireModel();
//    MarbleSolitaireTextView viewEuropean = new MarbleSolitaireTextView(europeanModel);
//    System.out.println(viewEuropean);
//    europeanModel.move(3, 1, 3, 3);
//    System.out.println(viewEuropean);

    TriangleSolitaireModel triangleModel = new TriangleSolitaireModel(7, 2,8);
    TriangleSolitaireTextView viewTriangle = new TriangleSolitaireTextView(triangleModel);
    System.out.println(viewTriangle);

    triangleModel.move(0, 6,2,8);
    System.out.println(viewTriangle);

    TriangleSolitaireModel triangleModel5 = new TriangleSolitaireModel();
    TriangleSolitaireTextView viewTriangle5 = new TriangleSolitaireTextView(triangleModel5);
    System.out.println(viewTriangle5);
    triangleModel5.move(2, 2,0,4);
    System.out.println(viewTriangle5);

    triangleModel5.move(3, 5,1,3);
    System.out.println(viewTriangle5);

  }
}

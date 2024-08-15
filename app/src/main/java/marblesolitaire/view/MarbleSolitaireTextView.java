package marblesolitaire.view;

import java.io.IOException;

import marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * Represents the view of the marble solitaire game shown
 * through text form
 */
public class MarbleSolitaireTextView implements MarbleSolitaireView {

  private final MarbleSolitaireModelState model;
  private final int modelArmThickness;
  private final int modelInvalidPos;
  private final int modelBoardSize;
  protected final Appendable destination;

  //Constructor
  public MarbleSolitaireTextView(MarbleSolitaireModelState model) {
    if (model == null) {
      throw new IllegalArgumentException("Argument cannot be null!");
    }
    this.model = model;
    this.modelArmThickness = (model.getBoardSize() + 2) / 3;
    this.modelInvalidPos = this.modelArmThickness - 1;
    this.modelBoardSize = model.getBoardSize();
    this.destination = System.out;
  }

  //Constructor two
  public MarbleSolitaireTextView(MarbleSolitaireModelState model, Appendable destination) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    if (destination == null) {
      throw new IllegalArgumentException("Destination cannot be null!");
    }
    this.model = model;
    this.modelArmThickness = (model.getBoardSize() + 2) / 3;
    this.modelInvalidPos = this.modelArmThickness - 1;
    this.modelBoardSize = model.getBoardSize();
    this.destination = destination;

  }

  /**
   * To view the game board as a string
   *
   * @return a string where O represent marbles, _ represent empty cells,
   * and blank spaces represent invalid cells
   */
  public String toString() {
    StringBuilder output = new StringBuilder();

    for (int i = 0; i < this.modelBoardSize; i++) {
      for (int j = 0; j < this.modelBoardSize; j++) {
        //checks if top or bottom area of board are invalid, if so it breaks loop and does not print
        //spaces beyond final marble slot in row
        if (this.model.getSlotAt(i, j).equals(MarbleSolitaireModelState.SlotState.Invalid) &&
                (j >= this.modelBoardSize - this.modelInvalidPos) && (i < this.modelInvalidPos || i >= this.modelBoardSize - this.modelInvalidPos)) {
          break;
        }

        switch (this.model.getSlotAt(i, j)) {
          case Marble:
            output.append("O");
            break;
          case Invalid:
            output.append(" ");
            break;
          case Empty:
            output.append("_");
            break;
        }

        if (j < this.modelBoardSize - 1 && this.addSpace(i, j)) {
          output.append(" ");
        }
      }
      if (i < this.modelBoardSize - 1) {
        output.append("\n");
      }
    }
    return output.toString();
  }

  /**
   * Render the board to the provided data destination. The board should be rendered exactly
   * in the format produced by the toString method above
   *
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Override
  public void renderBoard() throws IOException {
    try {
      this.destination.append(this.toString());
    } catch (IOException e) {
      throw new IOException(e);
    }
  }

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Override
  public void renderMessage(String message) throws IOException {
    try {
      this.destination.append(message);
    } catch (IOException e) {
      throw new IOException();
    }
  }

  /**
   * Determines to add space after valid slot
   *
   * @param row row of slot
   * @param col column of slot
   * @return if the string should have a space after the specified marble
   */
  private boolean addSpace(int row, int col) {
    //checks if slot is in top or bottom areas of board
    if (row < this.modelInvalidPos || row >= this.modelBoardSize - this.modelInvalidPos) {
      //if in top or bottom area, check if the current column is the last column
      //if not a final column and not a marble, will return true to add space, and vice versa.
      return col < this.modelBoardSize - this.modelArmThickness
              //if beyond cross-section, check if slot is a marble or empty
              || ((this.model.getSlotAt(row, col).equals(MarbleSolitaireModelState.SlotState.Marble)
             // || (this.model.getSlotAt(row, col).equals(MarbleSolitaireModelState.SlotState.Empty)
              //if next slot to right of marble is invalid, then do not add space
              && !(this.model.getSlotAt(row, col + 1).equals(MarbleSolitaireModelState.SlotState.Invalid))));
    } else {
      return true;
    }
  }
}
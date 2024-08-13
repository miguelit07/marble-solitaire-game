package marblesolitaire.view;

import java.io.IOException;

import marblesolitaire.model.hw02.MarbleSolitaireModelState;

public class TriangleSolitaireTextView implements MarbleSolitaireView {

  private final MarbleSolitaireModelState model;
  private final int modelRows;
  private final int modelCols;
  protected final Appendable destination;

  //Constructor one
  public TriangleSolitaireTextView(MarbleSolitaireModelState model) {
    if (model == null) {
      throw new IllegalArgumentException("Argument cannot be null!");
    }
    this.model = model;
    this.modelRows = (model.getBoardSize() + 1 ) / 2;
    this.modelCols = model.getBoardSize();
    this.destination = System.out;
  }

  //Constructor two
  public TriangleSolitaireTextView(MarbleSolitaireModelState model, Appendable destination) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    if (destination == null) {
      throw new IllegalArgumentException("Destination cannot be null!");
    }
    this.model = model;
    this.modelRows = (model.getBoardSize() + 1 ) / 2;
    this.modelCols = model.getBoardSize();
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

    //Iterate through board
    for (int i = 0; i < this.modelRows; i++) {
      for (int j = 0; j < this.modelCols; j++) {
        //Calculates distance of current row to bottom row
        int distanceFromRow = Math.abs(i - this.modelRows) - 1;

        //Checks if top or bottom area of board are invalid, if so it breaks loop and does not print
        //spaces beyond final marble slot in row
        if (this.model.getSlotAt(i, j).equals(MarbleSolitaireModelState.SlotState.Invalid) &&
                (j >= this.modelCols - distanceFromRow)) {
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

        if (j < this.modelCols - 1 && this.addSpace(i, j)) {
          output.append(" ");
        }
      }
      if (i < this.modelCols - 1) {
        output.append("\n");
      }
    }
    return output.toString();
  }

  /**
   * Determines to add space after valid slot
   *
   * @param row row of slot
   * @param col column of slot
   * @return if the string should have a space after the specified marble
   */
  private boolean addSpace(int row, int col) {
    if (this.model.getSlotAt(row, col).equals(MarbleSolitaireModelState.SlotState.Marble)) {
      return this.model.getSlotAt(row, col).equals(MarbleSolitaireModelState.SlotState.Invalid);
    }
    return false;
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
}

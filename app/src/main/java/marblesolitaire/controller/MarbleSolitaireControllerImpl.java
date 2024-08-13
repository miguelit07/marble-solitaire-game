package marblesolitaire.controller;

import java.io.IOException;
import java.util.Scanner;

import marblesolitaire.model.hw02.MarbleSolitaireModel;
import marblesolitaire.model.hw02.MarbleSolitaireModelState;
import marblesolitaire.view.MarbleSolitaireView;

/**
 * Implements the MarbleSolitaireController interface
 * to be used as a controller for a MarbleSolitaire game,
 * where allows users to play the game by handling inputs
 * ,executing moves, and updating the view
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {

  private final MarbleSolitaireModel model;
  private final MarbleSolitaireView view;
  private final Readable readable;
  private int fromRow;
  private int fromCol;
  private int toRow;
  private int toCol;

  /**
   * Controller implementation constructor
   *
   * @param model    game model object
   * @param view     game view object
   * @param readable readable object to read input
   * @throws IllegalArgumentException if any parameters are null
   */
  public MarbleSolitaireControllerImpl(MarbleSolitaireModel model, MarbleSolitaireView view, Readable readable)
          throws IllegalArgumentException {
    if (model == null || view == null || readable == null) {
      throw new IllegalArgumentException("Null parameters not allowed");
    }
    this.model = model;
    this.view = view;
    this.readable = readable;
    //position fields are initially set to -1. -1 represents a position field that has been not set.
    this.fromRow = -1;
    this.fromCol = -1;
    this.toRow = -1;
    this.toCol = -1;
  }

  /**
   * This method starts a new game of Marble Solitaire
   * that the user can interact with and play.
   *
   * @throws IllegalStateException only if the controller is unable to
   *                               successfully read input or transmit output
   */
  @Override
  public void playGame() throws IllegalStateException {
    try {
      //Renders initial starting game board (if game is not over)
      if (!this.model.isGameOver()) {
        this.view.renderBoard();
        this.view.renderMessage("\nScore: " + this.model.getScore());
        this.view.renderMessage("\nMove by inputting from row, from col, to row, to col, \n" +
                "in that order or all in one line (row/col starts at position 1 1). \n" +
                "Type input: ");
      }
      //User input
      Scanner scanner = new Scanner(readable);
      while (scanner.hasNextLine()) {
        try {
          String line = scanner.nextLine().trim();

          //Check if user quits game
          if (line.equalsIgnoreCase("q")) {
            this.view.renderMessage("Game quit!\n");
            this.view.renderMessage("State of game when quit:\n");
            this.view.renderBoard();
            this.view.renderMessage("\nScore: " + this.model.getScore());
            break;
          }

          //Call helper method to handle/process user input
          this.processInput(line);

          //Call helper method to determine to move in game model
          this.moveOffUserInput();

          //Checks if game is over, if so ends game
          if (this.model.isGameOver()) {
            this.view.renderMessage("Game Over! \n");
            this.view.renderBoard();
            this.view.renderMessage("\nScore: " + this.model.getScore());
            break;
          }

        } catch (NumberFormatException e) {
          //If user writes invalid input (non-positive number and not 'q'/'Q').
          //prompt user to try again
          this.view.renderMessage("Invalid input, try again: ");
        }
      }
      scanner.close();
    } catch (IOException e) {
      throw new IllegalStateException("Illegal state" + e.getMessage(), e);
    }
  }

  /**
   * Helper method to process all formats of user input
   * while playing the game
   *
   * @param line string that user types
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  private void processInput(String line) throws IOException {
    //If user inputs single character in line
    if (line.length() == 1) {
      this.processUserSingleInt(line);
      //If User inputs all four characters in single line
    } else if (line.length() > 1) {
      this.processUserFourInt(line);
    } else {
      //Handles when length of user input line is 0
      try {
        this.view.renderMessage("Invalid input, line must have at least one character." +
                "\n Try again:");
      } catch (IOException e) {
        throw new IOException(e);
      }
    }
  }

  /**
   * Helper method for processInput. Handles and processes user input if
   * user only inputs one single character on one line at a time
   *
   * @param line line that user types
   */
  private void processUserSingleInt(String line) {

    //Check through unset position fields,
    //if unset, parse through line and set respectively to each position
    if (this.fromRow == -1) {
      this.fromRow = Integer.parseInt(line) - 1;
    } else if (this.fromCol == -1) {
      this.fromCol = Integer.parseInt(line) - 1;
    } else if (this.toRow == -1) {
      this.toRow = Integer.parseInt(line) - 1;
    } else if (this.toCol == -1) {
      this.toCol = Integer.parseInt(line) - 1;
    }
  }

  /**
   * Helper method for playGame. Handles and processes user input if
   * user four character inputs all row/col locations in one single line (e.g. 4 2 4 4)
   *
   * @param line line that user types
   */
  private void processUserFourInt(String line) {
    //Split line by spaces and then parse through numbers found,
    //set numbers to respective position fields
    String[] splitLine = line.split(" ");
    this.fromRow = Integer.parseInt(splitLine[0]) - 1;
    this.fromCol = Integer.parseInt(splitLine[1]) - 1;
    this.toRow = Integer.parseInt(splitLine[2]) - 1;
    this.toCol = Integer.parseInt(splitLine[3]) - 1;
  }


  /**
   * This is a helper method that determines if all position fields have been set before
   * moving and move based off user input
   *
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  private void moveOffUserInput() throws IOException {
    //Check if all field positions have been set
    if (fromRow != -1 && fromCol != -1 && toRow != -1 && toCol != -1) {
      //Check if move is possible using helper method
      if (this.isMovePossible(this.fromRow, this.fromCol, this.toRow, this.toCol)) {
        this.model.move(this.fromRow, this.fromCol, this.toRow, this.toCol);
        //If move is invalid, prompt user to try again
      } else {
        this.view.renderMessage("Invalid move. Play again. \n");
      }
      //Render new board after moving
      try {
        this.view.renderBoard();
        this.view.renderMessage("\nScore: " + this.model.getScore());
      } catch (IllegalStateException e) {
        throw new IOException(e);
      }

      //Reset position fields for the next move
      fromRow = -1;
      fromCol = -1;
      toRow = -1;
      toCol = -1;
    }
  }

  /**
   * Helper method to determine if a move in the game model board is possible
   *
   * @param fromRow the row number of the position to be moved from
   * @param fromCol the col number of the position to be moved from
   * @param toRow   the row number of the position to be moved to
   * @param toCol   the col number of the position to be moved to
   * @return if a move is possible or not
   */
  private boolean isMovePossible(int fromRow, int fromCol, int toRow, int toCol) {
    //Check if to position is empty
    if (this.model.getSlotAt(toRow, toCol) != MarbleSolitaireModelState.SlotState.Empty) {
      return false;
    }

    //Check if starting cell and middle cell are marbles
    if ((Math.abs(fromRow - toRow) == 2 && fromCol == toCol) ||
            (Math.abs(fromCol - toCol) == 2 && fromRow == toRow)) {
      int midRow = (fromRow + toRow) / 2;
      int midCol = (fromCol + toCol) / 2;
      return this.model.getSlotAt(midRow, midCol) == MarbleSolitaireModelState.SlotState.Marble
              && this.model.getSlotAt(fromRow, fromCol) == MarbleSolitaireModelState.SlotState.Marble;
    }
    return false;
  }
}





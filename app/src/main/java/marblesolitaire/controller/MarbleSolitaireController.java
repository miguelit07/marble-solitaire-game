package marblesolitaire.controller;

/**
 * This interface is a controller for MarbleSolitaire
 * that is responsible for interpreting user inputs
 * and communicating with the model to execute game moves,
 * and update the user view of the game.
 */
public interface MarbleSolitaireController {

  /**
   * This method should start a new game of Marble Solitaire
   * that the user can interact with and play.
   *
   * @throws IllegalStateException only if the controller is unable to
   *                               successfully read input or transmit output
   */
  void playGame() throws IllegalStateException;
}

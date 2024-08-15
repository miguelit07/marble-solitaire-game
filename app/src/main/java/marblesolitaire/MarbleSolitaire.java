package marblesolitaire;

import java.io.InputStreamReader;

import marblesolitaire.controller.MarbleSolitaireController;
import marblesolitaire.controller.MarbleSolitaireControllerImpl;
import marblesolitaire.model.hw02.EnglishSolitaireModel;
import marblesolitaire.model.hw02.MarbleSolitaireModel;
import marblesolitaire.model.hw04.EuropeanSolitaireModel;
import marblesolitaire.model.hw04.TriangleSolitaireModel;
import marblesolitaire.view.MarbleSolitaireTextView;
import marblesolitaire.view.MarbleSolitaireView;
import marblesolitaire.view.TriangleSolitaireTextView;

/**
 * Main method to play MarbleSolitaire game
 */
public final class MarbleSolitaire {
  public static void main(String[] args) {
    MarbleSolitaireModel model = null;
    MarbleSolitaireView view = null;
    MarbleSolitaireController controller = null;

    //Default
    String gameType = "";
    int size = 0;
    int sRow = -1;
    int sCol = -1;

    //Handle user command line arguments
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "english":
        case "european":
        case "triangular":
          gameType = args[i];
          break;
        case "-size":
          if (i + 1 < args.length) {
            size = Integer.parseInt(args[++i]);
          }
          break;
        case "-hole":
          if (i + 2 < args.length) {
            sRow = Integer.parseInt(args[++i]);
            sCol = Integer.parseInt(args[++i]);
          }
          break;
        default:
          break;
      }
    }

    //Create model based on given game type
    switch (gameType) {
      case "english":
        if (size > 0) {
          if (sRow != -1 && sCol != -1) {
            model = new EnglishSolitaireModel(size, sRow, sCol);
          } else {
            model = new EnglishSolitaireModel(size);
          }
        } else {
          if (sRow != -1 && sCol != -1) {
            model = new EnglishSolitaireModel(sRow, sCol);
          } else {
            model = new EnglishSolitaireModel();
          }
        }
        view = new MarbleSolitaireTextView(model);
        break;
      case "european":
        if (size > 0) {
          if (sRow != -1 && sCol != -1) {
            model = new EuropeanSolitaireModel(size, sRow, sCol);
          } else {
            model = new EuropeanSolitaireModel(size);
          }
        } else {
          if (sRow != -1 && sCol != -1) {
            model = new EuropeanSolitaireModel(sRow, sCol);
          } else {
            model = new EuropeanSolitaireModel();
          }
        }
        view = new MarbleSolitaireTextView(model);
        break;
      case "triangular":
        if (size > 0) {
          if (sRow != -1 && sCol != -1) {
            model = new TriangleSolitaireModel(size, sRow, sCol);
          } else {
            model = new TriangleSolitaireModel(size);
          }
        } else {
          if (sRow != -1 && sCol != -1) {
            model = new TriangleSolitaireModel(sRow, sCol);
          } else {
            model = new TriangleSolitaireModel();
          }
        }
        view = new TriangleSolitaireTextView(model);
        break;
      default:
        throw new IllegalArgumentException("Illegal game type");
    }

    controller = new MarbleSolitaireControllerImpl(model, view, new InputStreamReader(System.in));
    controller.playGame();

  }
}
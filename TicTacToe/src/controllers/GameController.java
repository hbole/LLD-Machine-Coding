package controllers;

import exceptions.DuplicateSymbolException;
import exceptions.InvalidMoveException;
import exceptions.InvalidNumberOfPlayerException;
import exceptions.MaximumAllowedBotsException;
import models.Game;
import models.GameState;
import models.Player;
import strategies.winningstrategy.GameWinningStrategy;

import java.util.List;

public class GameController {
    //In controller class we will write all the methods which client will require.

    public Game startGame(int dimension, List<Player> players, List<GameWinningStrategy> winningStrategies)
            throws MaximumAllowedBotsException, InvalidNumberOfPlayerException, DuplicateSymbolException {
        return Game.getBuilder()
                .setDimensions(dimension)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();
    }

    public void makeMove(Game game) throws InvalidMoveException {
        game.makeMove();
    }

    public GameState getGameState(Game game) {
        return game.getGameState();
    }

    public void undoMove(Game game) {
        game.undoMove();
    }

    public void displayBoard(Game game) {
        game.getBoard().displayBoard();
    }

    public Player getWinner(Game game) {
        return game.getWinner();
    }
}

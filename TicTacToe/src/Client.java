import controllers.GameController;
import exceptions.DuplicateSymbolException;
import exceptions.InvalidMoveException;
import exceptions.InvalidNumberOfPlayerException;
import exceptions.MaximumAllowedBotsException;
import models.*;
import strategies.winningstrategy.ColumnWinningStrategy;
import strategies.winningstrategy.DiagonalWinningStrategy;
import strategies.winningstrategy.GameWinningStrategy;
import strategies.winningstrategy.RowWinningStrategy;

import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws MaximumAllowedBotsException, InvalidNumberOfPlayerException, DuplicateSymbolException, InvalidMoveException {
        GameController gameController = new GameController();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the board dimensions");
        int dimensions = sc.nextInt();

//        System.out.println("Enter the number of players");
        List<Player> players = List.of(
                new Player("Harshit", new Symbol('X')),
                new Bot("IUY8787", new Symbol('O'), BotDifficultyLevel.EASY)
        );

        //Game Winning Strategies
        List<GameWinningStrategy> winningStrategies = List.of(
                new RowWinningStrategy(),
                new ColumnWinningStrategy(),
                new DiagonalWinningStrategy()
        );

        Game game = gameController.startGame(dimensions, players, winningStrategies);

        while(gameController.getGameState(game).equals(GameState.IN_PROGRESS)) {
            gameController.displayBoard(game);

            System.out.println("Do you want to undo?");
            String moveType = sc.next();

            if(moveType.equalsIgnoreCase("y")) {
                gameController.undoMove(game);
                continue;
            } else {
                gameController.makeMove(game);
            }
        }

        // While loop will be over if the game has ENDED or game is DRAW.
        System.out.println("Game finished");

        if(gameController.getGameState(game).equals(GameState.ENDED)) {
            Player playerWon = gameController.getWinner(game);
            System.out.println(playerWon.getName() + " is the winner");
        } else {
            System.out.println("Game DRAWN!!");
        }

        gameController.displayBoard(game);
    }
}

package models;

import exceptions.DuplicateSymbolException;
import exceptions.InvalidMoveException;
import exceptions.InvalidNumberOfPlayerException;
import exceptions.MaximumAllowedBotsException;
import strategies.winningstrategy.GameWinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private Player winner;
    private GameState gameState;
    private int nextMovePlayerIndex;
    private List<GameWinningStrategy> winningStrategies;

    private Game(int dimension, List<Player> players, List<GameWinningStrategy> winningStrategies) {
        this.moves = new ArrayList<>();
        this.gameState = GameState.IN_PROGRESS;
        this.nextMovePlayerIndex = 0;
        this.winner = null;
        this.winningStrategies = winningStrategies;
        this.players = players;
        this.board = new Board(dimension);
    }

    public static GameBuilder getBuilder() {
        return new GameBuilder();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextMovePlayerIndex() {
        return nextMovePlayerIndex;
    }

    public void setNextMovePlayerIndex(int nextMovePlayerIndex) {
        this.nextMovePlayerIndex = nextMovePlayerIndex;
    }

    public List<GameWinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<GameWinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    private boolean checkWinner(Move move) {
        for(GameWinningStrategy winningStrategy : this.winningStrategies) {
            if(winningStrategy.checkWinner(this.board, move)) {
                return true;
            }
        }
        return false;
    }

    private void resetWinningStrategies(Move move) {
        for (GameWinningStrategy winningStrategy : this.winningStrategies) {
            winningStrategy.handleUndo(this.board, move);
        }
    }

    private boolean validateCurrentMove(Move move) {
        int currentMoveRow = move.getCell().getRow();
        int currentMoveCol = move.getCell().getCol();

        if(currentMoveRow < 0 || currentMoveRow >= board.getSize()) {
            return false;
        }

        if(currentMoveCol < 0 || currentMoveCol >= board.getSize()) {
            return false;
        }

        Cell currentCell = board.getBoard().get(currentMoveRow).get(currentMoveCol);

        return currentCell.getCellState().equals(CellState.EMPTY);
    }

    public void makeMove() throws InvalidMoveException {
        Player currentPlayer = players.get(this.nextMovePlayerIndex);

        System.out.println("Current Player: " + currentPlayer.getName() + "(" + currentPlayer.getSymbol().getaChar() + ")");
        Move currentMove = currentPlayer.executeMove(board);

        int currentMoveRow = currentMove.getCell().getRow();
        int currentMoveCol = currentMove.getCell().getCol();
        System.out.println(currentPlayer.getName() + " has made the move at row:" + currentMoveRow + ", col:" + currentMoveCol);

        if(!validateCurrentMove(currentMove)) {
            throw new InvalidMoveException("Player is trying to make an invalid move");
        }

        this.nextMovePlayerIndex = (this.nextMovePlayerIndex + 1) % players.size();

        Cell cell = board.getBoard().get(currentMoveRow).get(currentMoveCol);
        cell.setPlayer(currentPlayer);
        cell.setCellState(CellState.FILLED);

        Move finalMove = new Move(cell, currentPlayer);
        moves.add(finalMove);

        //Check if the move made by the player is winning move or not;
        if(checkWinner(finalMove)) {
            this.gameState = GameState.ENDED;
            this.winner = currentPlayer;
        } else if(moves.size() == board.getSize() * board.getSize()) {
            this.gameState = GameState.DRAW;
        }
    }

    public void undoMove() {
        if(moves.isEmpty()) {
            System.out.println("We can't perform UNDO operations as there are no moves.");
        }

        Move lastMove = moves.getLast();
        moves.removeLast();

        Cell lastCell = lastMove.getCell();
        int lastCellRow = lastCell.getRow();
        int lastCellCol = lastCell.getCol();
        lastCell.setPlayer(null);
        lastCell.setCellState(CellState.EMPTY);

        //resetting the player index;
        nextMovePlayerIndex -= 1;
        nextMovePlayerIndex = (nextMovePlayerIndex + players.size()) % players.size();

        //Reset the count in the winning strategies
        resetWinningStrategies(lastMove);
    }

    public static class GameBuilder {
        private List<Player> players;
        private List<GameWinningStrategy> winningStrategies;
        private int dimensions;

        private GameBuilder() {
            this.players = new ArrayList<>();
            this.winningStrategies = new ArrayList<>();
            this.dimensions = 0;
        }

        public GameBuilder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public GameBuilder setWinningStrategies(List<GameWinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public GameBuilder setDimensions(int dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        private boolean validatePlayerSymbolsForAll() {
            boolean doesAllPlayersHaveUniqueSymbol = true;

            Set<Character> playerSymbols = new HashSet<>();
            for(Player player : players) {
                Symbol playerSymbol = player.getSymbol();
                if(playerSymbols.contains(playerSymbol.getaChar())) {
                    doesAllPlayersHaveUniqueSymbol = false;
                    break;
                } else {
                    playerSymbols.add(playerSymbol.getaChar());
                }
            }

            return doesAllPlayersHaveUniqueSymbol;
        }

        private boolean validateBotCount() {
            int botCount = 0;
            for(Player player : players) {
                if(player.getPlayerType().equals(PlayerType.BOT)) {
                    botCount++;
                }
            }

            return botCount <= 1;
        }

        private void validate() throws InvalidNumberOfPlayerException, DuplicateSymbolException, MaximumAllowedBotsException {
            //validate number of players
            if(players.size() != dimensions - 1) {
                throw new InvalidNumberOfPlayerException("Number of players should be 1 less than the dimensions");
            }

            //validate all players have different symbols
            if(!validatePlayerSymbolsForAll()) {
                throw new DuplicateSymbolException("Symbol for all players should be unique");
            }

            //validate number of bots in the game
            if(!validateBotCount()) {
                throw new MaximumAllowedBotsException("Maximum 1 bot is allowed per game");
            }
        }

        public Game build() throws MaximumAllowedBotsException, InvalidNumberOfPlayerException, DuplicateSymbolException {
           //validations
            validate();

            return new Game(dimensions, players, winningStrategies);
        }
    }
}

package strategies.winningstrategy;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements GameWinningStrategy {
    private final Map<Symbol, Integer> rightDiagonalMaps = new HashMap<>();
    private final Map<Symbol, Integer> leftDiagonalMaps = new HashMap<>();

    @Override
    public boolean checkWinner(Board board, Move move) {
        int dimensions = board.getSize();
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        Map<Symbol, Integer> mapToCheck;
        System.out.println("Row: " + row + " Col: " + col);

        if(row == col) {
            if(!leftDiagonalMaps.containsKey(symbol)) {
                leftDiagonalMaps.put(symbol, 0);
            }

            int currentCount = leftDiagonalMaps.get(symbol);
            leftDiagonalMaps.put(symbol, currentCount+1);
        }

        if(row + col == dimensions - 1) {
            if(!rightDiagonalMaps.containsKey(symbol)) {
                rightDiagonalMaps.put(symbol, 0);
            }

            int currentCount = rightDiagonalMaps.get(symbol);
            rightDiagonalMaps.put(symbol, currentCount+1);
        }

        if (row == col && leftDiagonalMaps.get(symbol).equals(board.getSize())) {
            return true;
        }

        if (row + col == board.getSize() - 1 && rightDiagonalMaps.get(symbol).equals(board.getSize())) {
            return true;
        }

        return false;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int dimension = board.getSize();
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        if(row == col) {
            leftDiagonalMaps.put(symbol, leftDiagonalMaps.get(symbol) - 1);
        }

        if(row + col == dimension - 1) {
            rightDiagonalMaps.put(symbol, rightDiagonalMaps.get(symbol) - 1);
        }
    }
}

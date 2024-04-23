package strategies.winningstrategy;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class RowWinningStrategy implements GameWinningStrategy {
    private final Map<Integer, Map<Symbol, Integer>> rowMaps = new HashMap<>();

    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();
        int dimensions = board.getSize();

        if(!rowMaps.containsKey(row)) {
            rowMaps.put(row, new HashMap<>());
        }

        Map<Symbol, Integer> rowMap = rowMaps.get(row);

        if(rowMap.containsKey((symbol))) {
            int currentCount = rowMap.get(symbol);
            rowMap.put(symbol, currentCount+1);
        } else {
            rowMap.put(symbol, 1);
        }

        return rowMap.get(symbol) == dimensions;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();

        Map<Symbol, Integer> rowMap = rowMaps.get(row);

        rowMap.put(symbol, rowMap.get(symbol) - 1);
    }
}

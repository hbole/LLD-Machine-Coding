package strategies.winningstrategy;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ColumnWinningStrategy implements GameWinningStrategy {
    private final Map<Integer, Map<Symbol, Integer>> colMaps = new HashMap<>();

    @Override
    public boolean checkWinner(Board board, Move move) {
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();
        int dimensions = board.getSize();

        if(!colMaps.containsKey(col)) {
            colMaps.put(col, new HashMap<>());
        }

        Map<Symbol, Integer> colMap = colMaps.get(col);

        if(colMap.containsKey((symbol))) {
            int currentCount = colMap.get(symbol);
            colMap.put(symbol, currentCount+1);
        } else {
            colMap.put(symbol, 1);
        }

        return colMap.get(symbol) == dimensions;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int col = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();

        Map<Symbol, Integer> colMap = colMaps.get(col);

        colMap.put(symbol, colMap.get(symbol) - 1);
    }
}

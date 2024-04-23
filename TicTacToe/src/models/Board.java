package models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private List<List<Cell>> board;

    public Board(int dimensions) {
        this.size = dimensions;
        this.board = new ArrayList<>();

        for(int i = 0; i < dimensions; i++) {
            List<Cell> rowCells = new ArrayList<>();
            for (int j = 0; j < dimensions; j++) {
                Cell currentCell = new Cell(i, j);
                rowCells.add(currentCell);
            }

            this.board.add(rowCells);
        }
    }

    public void displayBoard() {
        for(int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                Cell currentCell = this.board.get(i).get(j);

                if(currentCell.getCellState().equals(CellState.EMPTY)) {
                    System.out.print("|   |");
                } else {
                    System.out.print("| " + currentCell.getPlayer().getSymbol().getaChar() + " |");
                }
            }

            System.out.println();
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<List<Cell>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }
}

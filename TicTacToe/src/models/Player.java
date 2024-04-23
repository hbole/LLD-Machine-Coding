package models;

import java.util.Scanner;

public class Player {
    private Long id;
    private String name;
    private Symbol symbol;
    private PlayerType playerType;

    public Player(String name, Symbol symbol) {
        this.name = name;
        this.symbol = symbol;
        this.playerType = PlayerType.HUMAN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Move executeMove(Board board) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the row you want to make the move");
        int row = sc.nextInt();

        System.out.println("Enter the column you want to make the move");
        int col = sc.nextInt();

        Cell currentCell = new Cell(row, col);
        Move currentMove = new Move(currentCell, this);

        return currentMove;
    }
}

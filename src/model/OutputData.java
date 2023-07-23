package model;

import model.players.Player;
import model.basic.Tile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OutputData {
    private final Player turnPlayer;
    private final int round;

    private final List<Tile> playerHand;
    private final List<Tile> playerTable;
    private final List<Tile> ai1Table;
    private final List<Tile> ai2Table;
    private final List<Tile> ai3Table;
    private final List<Tile> tilesToDraw;

    public OutputData(Player turnPlayer, int round, List<Tile> playerHand, List<Tile> playerTable, List<Tile> ai1Table, List<Tile> ai2Table, List<Tile> ai3Table) {
        this.turnPlayer = turnPlayer;
        this.round = round;
        this.playerHand = playerHand;
        this.playerTable = playerTable;
        this.ai1Table = ai1Table;
        this.ai2Table = ai2Table;
        this.ai3Table = ai3Table;
        this.tilesToDraw = Stream.of(playerHand, playerTable, ai1Table, ai2Table, ai3Table).flatMap(List::stream).collect(Collectors.toList());
    }

    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public int getRound() {
        return round;
    }

    public List<Tile> getPlayerHand() {
        return playerHand;
    }

    public List<Tile> getPlayerTable() {
        return playerTable;
    }

    public List<Tile> getAi1Table() {
        return ai1Table;
    }

    public List<Tile> getAi2Table() {
        return ai2Table;
    }

    public List<Tile> getAi3Table() {
        return ai3Table;
    }

    public List<Tile> getTilesToDraw() {
        return tilesToDraw;
    }

    @Override
    public String toString() {
        return "OutputData{" +
                "turnPlayer=" + turnPlayer +
                ", round=" + round +
                ", playerHand=" + playerHand +
                ", playerTable=" + playerTable +
                ", ai1Table=" + ai1Table +
                ", ai2Table=" + ai2Table +
                ", ai3Table=" + ai3Table +
                ", tilesToDraw=" + tilesToDraw +
                '}';
    }
}
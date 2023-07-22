package application.core;

import model.players.Player;
import model.tiles.Tile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoundData {
    private final Player turnPlayer;
    private final int round;

    private final List<Tile> playerHand;
    private final List<Tile> playerTable;
    private final List<Tile> tilesToDraw;

    public RoundData(Player turnPlayer, int round, List<Tile> playerHand, List<Tile> playerTable) {
        this.turnPlayer = turnPlayer;
        this.round = round;
        this.playerHand = playerHand;
        this.playerTable = playerTable;
        this.tilesToDraw = Stream.concat(
                        playerHand.stream(),
                        playerTable.stream())
                .collect(Collectors.toList());
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

    public List<Tile> getTilesToDraw() {
        return tilesToDraw;
    }

    @Override
    public String toString() {
        return "RoundData{" +
                "turnPlayer=" + turnPlayer +
                ", round=" + round +
                ", playerHand=" + playerHand +
                ", playerTable=" + playerTable +
                ", tilesToDraw=" + tilesToDraw +
                '}';
    }
}

package model;

import model.players.Player;
import model.basic.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameState {
    private final Player turnPlayer;
    private final List<Player> allPlayers;
    private final int round;

    private final List<Tile> playerHand;
    private final List<Tile> playerKong;
    private final List<Tile> playerPung;
    private final Tile playerNewTile;
    private final List<Tile> playerTable;
    private final List<Tile> ai1Table;
    private final List<Tile> ai2Table;
    private final List<Tile> ai3Table;
    private final List<Tile> tilesToDraw;
    private final boolean ongoingPung;

    public GameState(Player turnPlayer, List<Player> allPlayers, int round, List<Tile> playerHand, List<Tile> playerKong,
                     List<Tile> playerPung, Tile playerNewTile, List<Tile> playerTable,
                     List<Tile> ai1Table, List<Tile> ai2Table, List<Tile> ai3Table, boolean ongoingPung) {
        this.turnPlayer = turnPlayer;
        this.allPlayers = allPlayers;
        this.round = round;
        this.playerHand = playerHand;
        this.playerKong = playerKong;
        this.playerPung = playerPung;
        this.playerNewTile = playerNewTile;
        this.playerTable = playerTable;
        this.ai1Table = ai1Table;
        this.ai2Table = ai2Table;
        this.ai3Table = ai3Table;
        this.ongoingPung = ongoingPung;

        this.tilesToDraw = Stream.of(playerHand, playerKong, playerPung, playerTable, ai1Table, ai2Table, ai3Table).flatMap(List::stream).collect(Collectors.toList());
        if (this.playerNewTile != null) {
            this.tilesToDraw.add(this.playerNewTile);
        }
    }

    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public List<Player> getAllPlayers() {
        return allPlayers;
    }

    public int getRound() {
        return round;
    }

    public List<Tile> getPlayerHand() {
        List<Tile> handWithNewTile = new ArrayList<>(playerHand);
        if (playerNewTile != null) {
            handWithNewTile.add(playerNewTile);
        }
        return handWithNewTile;
    }

    public List<Tile> getPlayerKong() {
        return playerKong;
    }

    public List<Tile> getPlayerPung() {
        return playerPung;
    }

    public Tile getPlayerNewTile() {
        return playerNewTile;
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

    public boolean isOngoingPung() {
        return ongoingPung;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "turnPlayer=" + turnPlayer +
                ", round=" + round +
                ", playerHand=" + playerHand +
                ", playerKong=" + playerKong +
                ", playerPung=" + playerPung +
                ", playerNewTile=" + playerNewTile +
                ", playerTable=" + playerTable +
                ", ai1Table=" + ai1Table +
                ", ai2Table=" + ai2Table +
                ", ai3Table=" + ai3Table +
                ", tilesToDraw=" + tilesToDraw +
                '}';
    }
}

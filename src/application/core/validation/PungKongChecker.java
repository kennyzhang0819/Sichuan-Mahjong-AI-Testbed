package application.core.validation;

import model.basic.Tile;
import model.players.Player;

import java.util.Collections;
import java.util.List;

public class PungKongChecker {
    private final Player player;
    private final List<Tile> playerHand;
    private final Tile newTile;

    public PungKongChecker(Player player, Tile newTile) {
//        if (player.getName() == "Player") {
//            System.out.println(player + " and " + newTile.toString());
//        }


        this.player = player;
        this.playerHand = player.getHand().toList();
        this.newTile = newTile;
    }

    public boolean canPung() {
        int tileCount = Collections.frequency(playerHand, newTile);
        return tileCount >= 2; // if there is a pair, can Pung
    }

    public boolean canKong() {
        int tileCount = Collections.frequency(playerHand, newTile);
        return tileCount >= 3; // if there is a triple, can Kong
    }

}

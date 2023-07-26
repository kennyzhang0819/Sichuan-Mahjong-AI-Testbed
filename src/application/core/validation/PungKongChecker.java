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
        return tileCount >= 3 || this.player.getHand().getPung().size() > 0; // if there is a triple, can Kong
    }

}

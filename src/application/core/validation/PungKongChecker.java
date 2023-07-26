package application.core.validation;

import model.basic.Tile;
import model.players.Player;
import model.tiles.Group;

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

    public boolean canNormalKong() {
        int tileCount = Collections.frequency(playerHand, newTile);
        return tileCount >= 3; // if there is a triple, can Kong
    }
    public boolean canAddKong() {
        List<Group> pungs = player.getHand().getPung();
        for (Group pung : pungs) {
            if (pung.toList().get(0) == newTile) {
                return true;
            }
        }
        return false;
    }

    public boolean canHiddenKong() {
        // Checks if player has 4 identical tiles in hand
        int tileCount = Collections.frequency(playerHand, newTile);
        return tileCount == 4; // If there are 4 identical tiles, can Hidden Kong
    }

}

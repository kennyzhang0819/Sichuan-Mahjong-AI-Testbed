package application.game.validation;

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
        if (this.player.isPlaying()) {
            return false;
        }
        int tileCount = Collections.frequency(playerHand, newTile);
        return tileCount >= 2;
    }

    public boolean canNormalKong() {
        if (this.player.isPlaying()) {
            return false;
        }
        int tileCount = Collections.frequency(playerHand, newTile);
        return tileCount >= 3;
    }
    public boolean canAddKong() {
        List<Group> pungs = this.player.getHand().getPung();
        for (Group pung : pungs) {
            if (newTile.equals(pung.toList().get(1))) {
                return true;
            }
        }
        return false;
    }

    public boolean canHiddenKong() {
        if (this.player.isWaiting()) {
            return false;
        }
        for (Tile tile : this.playerHand) {
            if (Collections.frequency(playerHand, tile) == 4) {
                return true;
            }
        }
        int tileCount = Collections.frequency(playerHand, newTile);
        return tileCount >= 3;
    }

}

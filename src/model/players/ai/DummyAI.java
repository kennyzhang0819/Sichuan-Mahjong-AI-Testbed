package model.players.ai;

import model.players.Player;
import model.tiles.Tile;

import java.util.List;

public class DummyAI extends Player {
    public DummyAI(String name, List<Tile> hand) {
        super(name, hand);
    }

    @Override
    public void action() {
        Tile tile = this.getHand().toList().get(0);
        this.plays(tile);
    }
}

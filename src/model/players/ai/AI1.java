package model.players.ai;

import model.players.Player;
import model.tiles.AI1TableTiles;
import model.tiles.Tile;
import model.tiles.Tiles;
import utils.TileUtils;

import java.util.List;

public class AI1 extends Player {
    private final AI1TableTiles aiTable;

    public AI1(String name, List<Tile> hand) {
        super(name, hand);
        this.aiTable = new AI1TableTiles();
    }

    @Override
    public void plays(Tile tile) {
        super.hand.remove(tile);
        TileUtils.resetEntity(tile);
        this.aiTable.add(tile);
    }

    @Override
    public void action() {
        Tile tile = this.getHand().toList().get(0);
        this.plays(tile);
    }

    @Override
    public Tiles getTable() {
        return this.aiTable;
    }
}

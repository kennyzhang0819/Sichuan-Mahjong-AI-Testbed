package model.players.ai;

import model.players.Player;
import model.tiles.AI1TableTiles;
import model.tiles.AI2TableTiles;
import model.tiles.Tile;
import model.tiles.Tiles;
import utils.TileUtils;

import java.util.List;

public class AI2 extends Player {
    private final AI2TableTiles aiTable;

    public AI2(String name, List<Tile> hand) {
        super(name, hand);
        this.aiTable = new AI2TableTiles();
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

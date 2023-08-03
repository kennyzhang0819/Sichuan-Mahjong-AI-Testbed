package model.players;

import aimodel.AI;
import aimodel.ProbabilityAI;
import model.basic.Tile;
import model.tiles.AI2TableTiles;
import model.tiles.Tiles;
import utils.TileUtils;

import java.util.List;

public class AI2 extends Player {
    private final AI2TableTiles aiTable;
    private final AI ai;

    public AI2(String name, List<Tile> hand) {
        super(name, hand);
        this.aiTable = new AI2TableTiles();
        this.ai = new ProbabilityAI();
    }

    @Override
    public void plays(Tile tile) {
        super.hand.remove(tile);
        TileUtils.resetEntity(tile);
        this.aiTable.add(tile);
    }

    @Override
    public void playAction() {
        ai.setHand(this.getHand());
        this.plays(ai.getTileToPlay());
    }

    @Override
    public PlayerActionEnum otherAction(Tile tile) {
        ai.setHand(this.getHand());
        if (this.containsPung() && ai.shouldPung(tile)) {
            return PlayerActionEnum.PUNG;
        } else if (this.containsChow() && ai.shouldChow(tile)) {
            return PlayerActionEnum.CHOW;
        } else if (this.containsKong() && ai.shouldKong(tile)) {
            return PlayerActionEnum.KONG;
        } else {
            return PlayerActionEnum.SKIP;
        }
    }


    @Override
    public Tiles getTable() {
        return this.aiTable;
    }
}

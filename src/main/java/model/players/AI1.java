package model.players;

import aimodel.AI;
import aimodel.ProbabilityAI;
import model.basic.Tile;

import java.util.List;

public class AI1 extends Player {
    private final AI ai;

    public AI1(String name, List<Tile> hand, int position) {
        super(name, hand, position);
        this.ai = new ProbabilityAI();
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
}

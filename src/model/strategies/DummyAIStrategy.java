package model.strategies;

import model.tiles.Tiles;

import java.util.Random;

public class DummyAIStrategy implements Strategy {

    private Tiles myHand;

    public DummyAIStrategy(Tiles myHand) {
        this.myHand = myHand;
    }
    @Override
    public void action() {
        int random = new Random().nextInt(this.myHand.toList().size());
        this.myHand.toList().get(random);
    }
}

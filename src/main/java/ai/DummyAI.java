package ai;

import model.tiles.Tiles;

import java.util.Random;

public class DummyAI {

    private Tiles myHand;

    public DummyAI(Tiles myHand) {
        this.myHand = myHand;
    }

    public void action() {
        int random = new Random().nextInt(this.myHand.toList().size());
        this.myHand.toList().get(random);
    }
}

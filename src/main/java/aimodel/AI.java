package aimodel;

import model.basic.Tile;
import model.tiles.HandTiles;

public interface AI {
    void setHand(HandTiles hand);
    boolean shouldPung(Tile tile);
    boolean shouldKong(Tile tile);
    boolean shouldChow(Tile tile);
    boolean shouldSkip(Tile tile);
    Tile getTileToPlay();
}

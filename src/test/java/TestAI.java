import aimodel.ProbabilityAI;
import model.basic.Tile;
import model.basic.TileTypeEnum;
import model.tiles.HandTiles;

import java.util.ArrayList;
import java.util.List;

public class TestAI {
    public static void main(String[] args) {
        List<Tile> hand = new ArrayList<Tile>() {{
            add(new Tile(TileTypeEnum.C, 6));
            add(new Tile(TileTypeEnum.C, 2));
            add(new Tile(TileTypeEnum.C, 7));
            add(new Tile(TileTypeEnum.C, 7));
            add(new Tile(TileTypeEnum.C, 8));
            add(new Tile(TileTypeEnum.C, 8));
            add(new Tile(TileTypeEnum.C, 1));
            add(new Tile(TileTypeEnum.C, 2));
            add(new Tile(TileTypeEnum.C, 3));
            add(new Tile(TileTypeEnum.C, 3));
            add(new Tile(TileTypeEnum.C, 4));
            add(new Tile(TileTypeEnum.C, 5));
            add(new Tile(TileTypeEnum.C, 5));
        }};
        ProbabilityAI ai = new ProbabilityAI();
        ai.setHand(new HandTiles(hand));
        System.out.println(ai.getTileToPlay());
    }
}

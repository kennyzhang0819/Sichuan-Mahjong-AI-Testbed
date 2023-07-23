package PlayerStatusCheckerTest;

import application.core.validation.PlayerStatusChecker;
import model.basic.Tile;
import model.players.Player;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        HandGenerator generator = new HandGenerator();
        Player p = new Player("TestPlayer", new ArrayList<>());
        PlayerStatusChecker validator1 = new PlayerStatusChecker(p);
        List<List<Tile>> randomTiles = new ArrayList<>();


//        for (int i = 0; i < 1000; i++) {
//            randomTiles.add(generator.generateRandomHand());
//        }
//        for (int i = 0; i < 100; i++) {
//            randomTiles.add(generator.generateLikelyStandardHuHand());
//        }
        for (int i = 0; i < 1000; i++) {
            randomTiles.add(generator.generatePairHand());
        }
        for (List<Tile> hand : randomTiles) {
            validator1.setTiles(hand);
            if (validator1.checkHu()) {
//                System.out.println("Testing " + hand.toString() + " | " + true);
            } else {
                System.out.println("Testing " + hand.toString() + " | " + false);
            }
        }

//        ArrayList<Tile> tiles = new ArrayList<Tile>() {{
//            add(new Tile(TileTypeEnum.B, 2));
//            add(new Tile(TileTypeEnum.B, 3));
//            add(new Tile(TileTypeEnum.B, 4));
//            add(new Tile(TileTypeEnum.B, 4));
//            add(new Tile(TileTypeEnum.B, 4));
//            add(new Tile(TileTypeEnum.B, 6));
//            add(new Tile(TileTypeEnum.B, 7));
//            add(new Tile(TileTypeEnum.B, 8));
//            add(new Tile(TileTypeEnum.B, 8));
//            add(new Tile(TileTypeEnum.B, 8));
//            add(new Tile(TileTypeEnum.B, 8));
//            add(new Tile(TileTypeEnum.B, 8));
//            add(new Tile(TileTypeEnum.B, 8));
//            add(new Tile(TileTypeEnum.B, 8));
//        }};
//        validator1.setTiles(tiles);
//        System.out.println(validator1.checkHu());
    }
}

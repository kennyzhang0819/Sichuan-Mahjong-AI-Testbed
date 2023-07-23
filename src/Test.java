import application.core.validation.PlayerStatusChecker;
import model.basic.Tile;
import model.basic.TileCategoryEnum;
import model.players.Player;
import model.tiles.HandTiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {

            Player p = new Player("test", new ArrayList<>());
            // A valid hand with four triples and a pair
            List<Tile> tiles1 = Arrays.asList(
                    new Tile(TileCategoryEnum.BAMBOO, 1), new Tile(TileCategoryEnum.BAMBOO, 2), new Tile(TileCategoryEnum.BAMBOO, 3),
                    new Tile(TileCategoryEnum.BAMBOO, 1), new Tile(TileCategoryEnum.BAMBOO, 1), new Tile(TileCategoryEnum.BAMBOO, 1),
                    new Tile(TileCategoryEnum.BAMBOO, 2), new Tile(TileCategoryEnum.BAMBOO, 2), new Tile(TileCategoryEnum.BAMBOO, 2),
                    new Tile(TileCategoryEnum.BAMBOO, 3), new Tile(TileCategoryEnum.BAMBOO, 3), new Tile(TileCategoryEnum.BAMBOO, 3),
                    new Tile(TileCategoryEnum.CHARACTER, 2), new Tile(TileCategoryEnum.CHARACTER, 2)
            );
            p.setHand(new HandTiles(tiles1));
            PlayerStatusChecker validator1 = new PlayerStatusChecker(p);
            System.out.println(validator1.checkHu());
    }
}

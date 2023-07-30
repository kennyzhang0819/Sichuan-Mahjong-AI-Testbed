import application.game.validation.PlayerStatusChecker;
import model.basic.Tile;
import model.basic.TileTypeEnum;
import model.players.Player;

import java.util.ArrayList;
import java.util.List;

public class StatusCheckerInitTest {
    public static void main(String[] args) {
        List<Tile> tiles = new ArrayList<Tile>() {{
            add(new Tile(TileTypeEnum.B, 2));
            add(new Tile(TileTypeEnum.B, 2));
            add(new Tile(TileTypeEnum.B, 2));
            add(new Tile(TileTypeEnum.C, 1));
            add(new Tile(TileTypeEnum.C, 1));
            add(new Tile(TileTypeEnum.C, 1));
            add(new Tile(TileTypeEnum.C, 1));
            add(new Tile(TileTypeEnum.C, 4));
            add(new Tile(TileTypeEnum.C, 3));
            add(new Tile(TileTypeEnum.C, 2));
            add(new Tile(TileTypeEnum.C, 6));
            add(new Tile(TileTypeEnum.C, 6));
            add(new Tile(TileTypeEnum.C, 7));
            add(new Tile(TileTypeEnum.C, 7));
        }};
        Player p2 = new Player("TestPlayer2", tiles);
        p2.getHand().addHiddenKong();
        p2.addTile(new Tile(TileTypeEnum.B, 2));
        new PlayerStatusChecker(p2, new Tile(TileTypeEnum.C, 6));
        System.out.println(p2.getHand());
        System.out.println(p2.getStatus());
    }
}

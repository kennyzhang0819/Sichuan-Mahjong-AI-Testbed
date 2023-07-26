package PlayerStatusCheckerTest;

import application.core.validation.PlayerStatusChecker;
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
            add(new Tile(TileTypeEnum.C, 4));
            add(new Tile(TileTypeEnum.C, 4));
            add(new Tile(TileTypeEnum.C, 4));
            add(new Tile(TileTypeEnum.C, 7));
            add(new Tile(TileTypeEnum.C, 8));
            add(new Tile(TileTypeEnum.C, 9));
            add(new Tile(TileTypeEnum.C, 8));
            add(new Tile(TileTypeEnum.C, 8));
            add(new Tile(TileTypeEnum.C, 8));
            add(new Tile(TileTypeEnum.C, 6));
        }};
        Player p2 = new Player("TestPlayer2", tiles);
        new PlayerStatusChecker(p2, new Tile(TileTypeEnum.B, 2));
        System.out.println(p2.getStatus());
    }
}

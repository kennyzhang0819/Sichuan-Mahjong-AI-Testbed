package utils;

import config.Config;;
import model.Tiles.Tile;

import java.util.List;

public class TileUtils {

    public static Tile getTileAt(int x, int y, List<Tile> tiles) {
        if (Config.PLAYER_HAND_LEFT_INDENT <= x
                && Config.PLAYER_HAND_TOP_INDENT - 20 <= y
                && y <= Config.PLAYER_HAND_TOP_INDENT + Config.TILE_HEIGHT) {
            for (Tile tile : tiles) {
                if (tile.x <= x && x <= tile.x + tile.width) {
                    return tile;
                }
            }
        }
        return null;
    }

    public static void moveTileUp(Tile tile) {
        tile.y -= 15;
    }

    public static void moveTileDown(Tile tile) {
        tile.y += 15;
    }


}

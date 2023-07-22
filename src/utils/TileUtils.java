package utils;

import config.Config;
import model.tiles.Tile;

import java.util.List;

public class TileUtils {

    public static void resetEntity(Tile tile) {
        tile.x = 0;
        tile.y = 0;
        tile.width = 0;
        tile.height = 0;
    }

    public static Tile getTileAt(int x, int y, List<Tile> tiles) {
        if (Config.PLAYER_HAND_LEFT_INDENT <= x
                && Config.PLAYER_HAND_TOP_INDENT <= y
                && y <= Config.PLAYER_HAND_TOP_INDENT + Config.TILE_HEIGHT) {
            for (Tile tile : tiles) {
                if (tile.x <= x && x <= tile.x + tile.width
                        && tile.y <= y && y <= tile.y + tile.height) {
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

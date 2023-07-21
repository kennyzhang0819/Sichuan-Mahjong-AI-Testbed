package application;

import model.Tile;

import java.util.List;

import static application.GamePanel.*;

public class TileUtils {

    public static Tile getTileAt(int x, int y, List<Tile> tiles) {
        if (LEFTINDENT <= x && TOPINDENT - 20 <= y && y <= TOPINDENT + (int) (TILESIZE * 1.3) + 40) {
            System.out.println("Region");
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

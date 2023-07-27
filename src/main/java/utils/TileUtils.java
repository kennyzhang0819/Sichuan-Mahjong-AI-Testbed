package utils;

import config.Config;
import model.basic.Tile;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TileUtils {

    public static void resetEntity(Tile tile) {
        tile.x = 1;
        tile.y = 1;
        tile.width = 30;
        tile.height = 30;
    }

    public static Tile getTileAt(int x, int y, List<Tile> tiles) {
        if (Config.PLAYER_HAND_X <= x && x <= Config.PLAYER_HAND_X + Config.PLAYER_HAND_WIDTH
                && Config.PLAYER_HAND_Y <= y && y <= Config.PLAYER_HAND_Y + Config.PLAYER_HAND_HEIGHT) {
            for (Tile tile : tiles) {
                if (tile.x <= x && x <= tile.x + tile.width
                        && tile.y <= y && y <= tile.y + tile.height) {
                    return tile;
                }
            }
        }
        return null;
    }

    private static final int STEPS = 60; // 60 frames in 1 second for smooth animation
    private static final int DELAY = 100 / STEPS; // delay in milliseconds

    public static void moveTileUp(Tile tile) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(new Runnable() {
            int stepsCompleted = 0;

            @Override
            public void run() {
                if (stepsCompleted < STEPS) {
                    tile.y -= 15.0 / STEPS;
                    stepsCompleted++;
                } else {
                    executor.shutdown();
                }
            }
        }, 0, DELAY, TimeUnit.MILLISECONDS);
    }

    public static void moveTileDown(Tile tile) {
        tile.y += 15;
    }


}

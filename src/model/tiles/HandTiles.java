package model.tiles;

import config.Config;
import model.basic.Tile;

import java.util.List;

public class HandTiles extends Tiles{
    public HandTiles(List<Tile> tiles) {
        super(tiles);
    }

    @Override
    public void updatePosition() {
        int fourteenth = 0;
        for (int i = 0; i < this.tiles.size(); i++) {
            Tile tile = this.tiles.get(i);
            if (i == 13) {
                fourteenth = Config.FOURTEENTH_TILE_INDENT;
            }
            tile.x = Config.PLAYER_HAND_LEFT_INDENT + Config.PLAYER_HAND_TILE_PADDING * tile.getIndex() + Config.TILE_WIDTH * tile.getIndex() + fourteenth;
            tile.y = Config.PLAYER_HAND_TOP_INDENT;
            tile.width = Config.TILE_WIDTH;
            tile.height = (int) (Config.TILE_HEIGHT);
        }
    }
}

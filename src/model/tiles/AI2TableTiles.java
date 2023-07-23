package model.tiles;

import config.Config;
import model.basic.Tile;

public class AI2TableTiles extends PlayerTableTiles {

    public AI2TableTiles() {
        super();
    }

    @Override
    public void updatePosition() {
        for (int i = 0; i < this.tiles.size(); i++) {
            Tile tile = this.tiles.get(i);
            tile.x = (int) (Config.AI2_TABLE_X + Config.TABLE_TILE_PADDING * tile.getIndex() + Config.TABLE_TILE_WIDTH * tile.getIndex());
            tile.y = Config.AI_TABLE_Y;
            tile.width = (int) (Config.TABLE_TILE_WIDTH);
            tile.height = (int) (Config.TABLE_TILE_HEIGHT);
        }
    }
}

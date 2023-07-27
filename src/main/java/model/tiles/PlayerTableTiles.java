package model.tiles;

import config.Config;
import model.basic.Tile;

public class PlayerTableTiles extends Tiles {

    public PlayerTableTiles() {
        super();
    }


    @Override
    public void updatePosition() {
        for (Tile tile : this.tiles) {
            tile.x = Config.PLAYER_TABLE_X + Config.TABLE_TILE_PADDING * tile.getIndex() + Config.TABLE_TILE_WIDTH * tile.getIndex();
            tile.y = Config.PLAYER_TABLE_Y;
            tile.width = Config.TABLE_TILE_WIDTH;
            tile.height = Config.TABLE_TILE_HEIGHT;
        }
    }

    @Override
    public void add(Tile tile) {
        this.tiles.add(tile);
        this.updateIndex();
        this.updatePosition();
    }

}

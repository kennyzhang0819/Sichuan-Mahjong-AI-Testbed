package model.Tiles;

import config.Config;

import java.util.List;

public class PlayerTableTiles extends Tiles{
    public PlayerTableTiles(List<Tile> tiles) {
        super(tiles);
    }

    @Override
    public void updatePosition() {
        for (int i = 0; i < this.tiles.size(); i++) {
            Tile tile = this.tiles.get(i);
            tile.x = Config.PLAYER_TABLE_LEFT_INDENT + Config.TILE_PADDING * tile.getIndex() + Config.TILE_WIDTH * tile.getIndex();
            tile.y = Config.PLAYER_TABLE_TOP_INDENT;
            tile.width = (int) (Config.TILE_WIDTH * 0.75);
            tile.height = (int) (Config.TILE_HEIGHT * 0.75);
        }
    }
}

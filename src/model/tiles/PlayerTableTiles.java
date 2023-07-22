package model.tiles;

import config.Config;

import java.util.List;

public class PlayerTableTiles extends Tiles {

    public PlayerTableTiles() {
        super();
    }

    public PlayerTableTiles(List<Tile> tiles) {
        super(tiles);
    }

    @Override
    public void updatePosition() {
        for (int i = 0; i < this.tiles.size(); i++) {
            Tile tile = this.tiles.get(i);
            tile.x = (int) (Config.PLAYER_TABLE_LEFT_INDENT + Config.TABLE_TILE_PADDING * tile.getIndex() + Config.TABLE_TILE_WIDTH * tile.getIndex());
            tile.y = Config.PLAYER_TABLE_TOP_INDENT;
            tile.width = (int) (Config.TABLE_TILE_WIDTH);
            tile.height = (int) (Config.TABLE_TILE_HEIGHT);
        }
    }

    @Override
    public void add(Tile tile) {
        this.tiles.add(tile);
        this.updateIndex();
        this.updatePosition();
    }
}

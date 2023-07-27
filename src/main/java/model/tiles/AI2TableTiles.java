package model.tiles;

import config.Config;
import model.basic.Tile;

public class AI2TableTiles extends Tiles {

    public AI2TableTiles() {
        super();
    }

    @Override
    public void updatePosition() {
        int currentLine = 0;
        int currentTile = 0;
        for (int i = 0; i < this.tiles.size(); i++) {
            Tile tile = this.tiles.get(i);
            tile.x = (int) (Config.AI2_TABLE_X + Config.TABLE_TILE_PADDING * currentTile + Config.TABLE_TILE_WIDTH * currentTile);
            tile.y = Config.AI_TABLE_Y + Config.TABLE_TILE_HEIGHT * currentLine + Config.TABLE_TILE_PADDING * currentLine;
            tile.width = (int) (Config.TABLE_TILE_WIDTH);
            tile.height = (int) (Config.TABLE_TILE_HEIGHT);
            currentTile++;
            if (currentTile == Config.AI_TABLE_NUM_TILES_PER_LINE) {
                currentLine++;
                currentTile = 0;
            }
        }
    }

    @Override
    public void add(Tile tile) {
        this.tiles.add(tile);
        this.updateIndex();
        this.updatePosition();
    }
}
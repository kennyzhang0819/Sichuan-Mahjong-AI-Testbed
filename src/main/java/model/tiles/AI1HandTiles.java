package model.tiles;

import application.config.Config;
import model.basic.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AI1HandTiles extends HandTiles {

    public AI1HandTiles(List<Tile> tiles) {
        super(tiles);
    }

    @Override
    public void updatePosition() {
        List<Group> pungsAndKongs = new ArrayList<>();
        if (super.getPung() != null)
            pungsAndKongs.addAll(super.getPung());
        if (super.getKong() != null)
            pungsAndKongs.addAll(super.getKong());

        if (pungsAndKongs.size() > 0) {
            int totalTilesSoFar = 0;
            for (int i = 0; i < pungsAndKongs.size(); i++) {
                Group group = pungsAndKongs.get(i);
                int totalTilesInGroup = group.toList().size();
                int groupX = Config.PLAYER_TABLE_X
                        + (Config.TABLE_TILE_WIDTH * totalTilesSoFar + Config.TABLE_TILE_PADDING * i);

                for (int j = 0; j < totalTilesInGroup; j++) {
                    Tile tile = group.toList().get(j);
                    tile.x = groupX + Config.TABLE_TILE_WIDTH * j;
                    tile.y = Config.PLAYER_PUNG_KONG_Y;
                    tile.width = Config.TABLE_TILE_WIDTH;
                    tile.height = Config.TABLE_TILE_HEIGHT;
                }
                totalTilesSoFar += totalTilesInGroup;
            }
        }
    }
}

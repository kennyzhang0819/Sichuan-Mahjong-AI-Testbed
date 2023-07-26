package model.tiles;

import config.Config;
import model.basic.Tile;

import java.util.*;

public class HandTiles extends Tiles {

    private Tile newTile;
    private List<Group> kong;
    private List<Group> pung;

    public HandTiles(List<Tile> tiles) {
        super(tiles);
        this.kong = new ArrayList<>();
        this.pung = new ArrayList<>();
    }

    @Override
    public void add(Tile tile) {
        if (this.newTile != null) {
            super.add(this.newTile);
        }
        this.newTile = tile;
        this.sort();
    }

    @Override
    public void remove(Tile tile) {
        if (tile == this.newTile) {
            this.newTile = null;
            this.sort();
            return;
        }
        for (Tile t : this.tiles) {
            if (t.equals(tile)) {
                this.tiles.remove(t);
                break;
            }
        }
        this.mergeNewWithHand();
        this.sort();
    }

    private void mergeNewWithHand() {
        if (this.newTile != null) {
            super.add(this.newTile);
            this.newTile = null;
        }
    }

    public void addPung(Tile tile) {
        List<Tile> pung = new ArrayList<Tile>() {{
            add(new Tile(tile.getType(), tile.getNumber()));
            add(new Tile(tile.getType(), tile.getNumber()));
            add(new Tile(tile.getType(), tile.getNumber()));
        }};
        this.pung.add(new Group(pung, GroupEnum.PUNG, 0));
        this.tiles.remove(tile);
        this.tiles.remove(tile);
        this.sort();
    }

    public void addNormalKong(Tile tile) {
        List<Tile> kong = this.generateNewKong(tile);
        this.kong.add(new Group(kong, GroupEnum.NORMAL_KONG, 0));
        this.tiles.removeAll(Collections.singletonList(tile));
        this.sort();
    }

    public void addAddKong(Tile tile) {
        for (Group group : this.pung) {
            if (group.toList().get(0) == tile) {
                this.pung.remove(group);
                break;
            }
        }
        List<Tile> kong = this.generateNewKong(tile);
        this.kong.add(new Group(kong, GroupEnum.ADD_KONG, 0));
        this.newTile = null;
        this.sort();
    }

    public void addHiddenKong(Tile tile) {
        List<Tile> kong = this.generateNewKong(tile);
        this.kong.add(new Group(kong, GroupEnum.HIDDEN_KONG, 0));
        this.tiles.removeAll(Collections.singletonList(tile));
        this.sort();
    }
    private List<Tile> generateNewKong(Tile tile) {
        return new ArrayList<Tile>() {{
            add(new Tile(tile.getType(), tile.getNumber()));
            add(new Tile(tile.getType(), tile.getNumber()));
            add(new Tile(tile.getType(), tile.getNumber()));
            add(new Tile(tile.getType(), tile.getNumber()));
        }};
    }

    public List<Group> getKong() {
        return kong;
    }

    public List<Group> getPung() {
        return pung;
    }

    public Tile getNewTile() {
        return newTile;
    }

    @Override
    public void updatePosition() {
        for (Tile tile : this.tiles) {
            tile.x = Config.PLAYER_HAND_LEFT_INDENT + Config.PLAYER_HAND_TILE_PADDING * tile.getIndex() + Config.TILE_WIDTH * tile.getIndex();
            tile.y = Config.PLAYER_HAND_TOP_INDENT;
            tile.width = Config.TILE_WIDTH;
            tile.height = Config.TILE_HEIGHT;
        }

        if (this.newTile != null) {
            this.newTile.x = Config.PLAYER_HAND_LEFT_INDENT
                    + Config.PLAYER_HAND_TILE_PADDING
                    + Config.TILE_WIDTH
                    + Config.FOURTEENTH_TILE_INDENT;
            this.newTile.y = Config.PLAYER_HAND_TOP_INDENT;
            this.newTile.width = Config.TILE_WIDTH;
            this.newTile.height = Config.TILE_HEIGHT;
        }


        List<Group> pungsAndKongs = new ArrayList<>();
        if (this.pung != null)
            pungsAndKongs.addAll(this.pung);
        if (this.kong != null)
            pungsAndKongs.addAll(this.kong);
        if (pungsAndKongs.size() > 0) {
            int totalGroups = pungsAndKongs.size(); // Number of groups

            for (int i = totalGroups - 1; i >= 0; i--) {
                Group group = pungsAndKongs.get(i);
                int totalTilesInGroup = group.toList().size(); // Number of tiles in group

                // Calculate the starting x-position of the group
                int groupX = Config.PLAYER_TABLE_X + Config.PLAYER_TABLE_WIDTH
                        - (Config.TABLE_TILE_WIDTH * totalTilesInGroup + Config.TABLE_TILE_PADDING) * (totalGroups - i);

                for (int j = 0; j < totalTilesInGroup; j++) {
                    Tile tile = group.toList().get(j);

                    // Calculate the x-position of the tile within the group
                    tile.x = groupX + Config.TABLE_TILE_WIDTH * j;
                    tile.y = Config.PLAYER_TABLE_Y;
                    tile.width = Config.TABLE_TILE_WIDTH;
                    tile.height = Config.TABLE_TILE_HEIGHT;
                }
            }
        }
    }
}

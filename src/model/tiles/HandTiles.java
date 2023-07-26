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
            this.tiles.add(this.newTile);
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
        this.sort();
    }

    public void addKong(Tile tile) {
        ArrayList<Tile> kong = new ArrayList<Tile>() {{
            add(tile);
            add(tile);
            add(tile);
            add(tile);
        }};
        this.kong.add(new Group(kong, GroupEnum.KONG, 0));
        this.tiles.removeAll(Collections.singletonList(tile));
        this.sort();
    }

    public void addPung(Tile tile) {
        List<Tile> pung = new ArrayList<Tile>() {{
            add(tile);
            add(tile);
            add(tile);
        }};
        this.pung.add(new Group(pung, GroupEnum.PUNG, 0));
        this.tiles.remove(tile);
        this.tiles.remove(tile);
        this.tiles.remove(tile);
        this.sort();
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
        for (int i = 0; i < this.tiles.size(); i++) {
            Tile tile = this.tiles.get(i);
            tile.x = Config.PLAYER_HAND_LEFT_INDENT + Config.PLAYER_HAND_TILE_PADDING * tile.getIndex() + Config.TILE_WIDTH * tile.getIndex();
            tile.y = Config.PLAYER_HAND_TOP_INDENT;
            tile.width = Config.TILE_WIDTH;
            tile.height = (int) (Config.TILE_HEIGHT);
        }

        if (this.newTile != null) {
            this.newTile.x = Config.PLAYER_HAND_LEFT_INDENT
                    + Config.PLAYER_HAND_TILE_PADDING
                    + Config.TILE_WIDTH
                    + Config.FOURTEENTH_TILE_INDENT;
            this.newTile.y = Config.PLAYER_HAND_TOP_INDENT;
            this.newTile.width = Config.TILE_WIDTH;
            this.newTile.height = (int) (Config.TILE_HEIGHT);
        }

        if (this.pung != null) {
            for (int i = 0; i < this.pung.size(); i++) {
                Group group = this.pung.get(i);
                for (int j = 0; j < group.toList().size(); j++) {
                    Tile tile = group.toList().get(j);
                    tile.x = Config.PLAYER_HAND_LEFT_INDENT
                            + Config.PLAYER_HAND_TILE_PADDING
                            + Config.TILE_WIDTH
                            + Config.FOURTEENTH_TILE_INDENT
                            + Config.TILE_WIDTH
                            + Config.PLAYER_HAND_TILE_PADDING
                            + Config.TILE_WIDTH * j;
                    tile.y = Config.PLAYER_HAND_TOP_INDENT;
                    tile.width = Config.TILE_WIDTH;
                    tile.height = (int) (Config.TILE_HEIGHT);
                }
            }
        }
    }
}

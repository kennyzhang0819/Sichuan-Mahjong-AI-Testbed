package model.tiles;

import application.config.Config;
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

    public void addAddKong() {
        assert this.newTile != null;
        for (Group group : this.pung) {
            if (this.newTile.equals(group.toList().get(0))) {
                this.pung.remove(group);
                break;
            }
        }
        List<Tile> kong = this.generateNewKong(this.newTile);
        this.kong.add(new Group(kong, GroupEnum.ADD_KONG, 0));
        this.newTile = null;
        this.sort();
    }

    public void addHiddenKong() {
        for (Tile tile : this.tiles) {
            if (Collections.frequency(this.tiles, tile) == 4) {
                List<Tile> kong = this.generateNewKong(tile);
                this.kong.add(new Group(kong, GroupEnum.HIDDEN_KONG, 0));
                this.tiles.removeAll(Collections.singletonList(tile));
                this.sort();
                break;
            } else if (Collections.frequency(this.tiles, tile) == 3 && tile.equals(this.newTile)) {
                List<Tile> kong = this.generateNewKong(tile);
                this.kong.add(new Group(kong, GroupEnum.HIDDEN_KONG, 0));
                this.tiles.removeAll(Collections.singletonList(tile));
                this.newTile = null;
                this.sort();
                break;
            }
        }
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

    public List<Group> getPungKong() {
        List<Group> pungKong = new ArrayList<>();
        pungKong.addAll(this.pung);
        pungKong.addAll(this.kong);
        return pungKong;
    }

    public Tile getNewTile() {
        return newTile;
    }

    @Override
    public void updatePosition() {
        for (int i = 0; i < this.tiles.size(); i++) {
            Tile tile = this.tiles.get(i);
            tile.x = Config.PLAYER_HAND_X + Config.PLAYER_HAND_TILE_PADDING * i + Config.TILE_WIDTH * i;
            tile.y = Config.PLAYER_HAND_TOP_INDENT;
            tile.width = Config.TILE_WIDTH;
            tile.height = Config.TILE_HEIGHT;
        }

        if (this.newTile != null) {
            this.newTile.x = Config.PLAYER_HAND_X
                    + Config.PLAYER_HAND_TILE_PADDING
                    + Config.TILE_WIDTH
                    + Config.FOURTEENTH_TILE_INDENT;
            this.newTile.y = Config.PLAYER_HAND_TOP_INDENT;
            this.newTile.width = Config.TILE_WIDTH;
            this.newTile.height = Config.TILE_HEIGHT;
        }
    }
}

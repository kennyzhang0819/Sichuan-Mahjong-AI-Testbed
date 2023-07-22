package model.tiles;

import java.util.ArrayList;
import java.util.List;

public class Tiles{

    protected final List<Tile> tiles;

    public Tiles() {
        this.tiles = new ArrayList<>();
        this.sort();
    }

    public Tiles(List<Tile> tiles) {
        this.tiles = tiles;
        this.sort();
    }

    public void add(Tile tile) {
        this.tiles.add(tile);
        this.sort();
    }


    public void remove(Tile tile) {
        for (Tile t : this.tiles) {
            if (t.equals(tile)) {
                this.tiles.remove(t);
                break;
            }
        }
        this.sort();
    }

    public void sort() {
        this.tiles.sort((tile1, tile2) -> {
            if (tile1.getCategory().equals(tile2.getCategory())) {
                return tile1.getNumber() - tile2.getNumber();
            } else {
                return tile1.getCategory().compareTo(tile2.getCategory());
            }
        });
        this.updateIndex();
        this.updatePosition();
    }

    protected void updateIndex() {
        for (int i = 0; i < this.tiles.size(); i++) {
            this.tiles.get(i).setIndex(i);
        }
    }

    public void updatePosition() {
        return;
    }

    public List<Tile> toList() {
        if (tiles == null) {
            return new ArrayList<>();
        }
        return tiles;
    }


}

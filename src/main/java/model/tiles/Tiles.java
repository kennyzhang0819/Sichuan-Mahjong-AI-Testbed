package model.tiles;

import model.basic.Tile;

import java.util.*;

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
            if (tile1.getType().equals(tile2.getType())) {
                return tile1.getNumber() - tile2.getNumber();
            } else {
                return tile1.getType().compareTo(tile2.getType());
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

    public Tile getLast() {
        return this.tiles.get(this.tiles.size() - 1);
    }

    public void removeLast() {
        this.tiles.remove(this.tiles.size() - 1);
    }

    public List<Tile> toList() {
        if (tiles == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(tiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tiles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tiles tiles1 = (Tiles) o;
        return Objects.equals(tiles, tiles1.tiles);
    }

    @Override
    public String toString() {
        return "Tiles{" +
                "tiles=" + tiles +
                '}';
    }
}

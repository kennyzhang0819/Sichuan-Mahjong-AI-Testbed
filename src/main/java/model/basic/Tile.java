package model.basic;

import java.util.Objects;

public class Tile extends Entity {
    private int index;
    private TileTypeEnum type;
    private int number;

    public Tile(TileTypeEnum type, int number) {
        super(0,0,0,0);
        this.type = type;
        this.number = number;
    }

    public TileTypeEnum getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return type + String.valueOf(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return number == tile.number && Objects.equals(type, tile.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, number);
    }
}

package model.tiles;

import model.basic.Tile;

import java.util.List;

public class Group extends Tiles{

    private final GroupEnum category;

    public Group(List<Tile> tiles, GroupEnum category) {
        super(tiles);
        this.category = category;
    }

    public GroupEnum getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Group{" +
                "category=" + category +
                ", tiles=" + tiles +
                '}';
    }
}

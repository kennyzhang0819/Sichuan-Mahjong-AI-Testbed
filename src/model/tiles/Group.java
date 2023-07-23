package model.tiles;

import model.basic.Tile;

import java.util.List;
import java.util.Objects;

public class Group extends Tiles{

    private final GroupEnum category;
    private int identification;

    public Group(List<Tile> tiles, GroupEnum category, int identification) {
        super(tiles);
        this.category = category;
        this.identification = identification;
    }

    public GroupEnum getCategory() {
        return category;
    }

    public Group getDup() {
        return new Group(this.tiles, this.category, this.identification++);
    }

    @Override
    public String toString() {
        return "Group{" +
                "category=" + category +
                ", tiles=" + tiles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Group group = (Group) o;
        return identification == group.identification && category == group.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category, identification);
    }
}

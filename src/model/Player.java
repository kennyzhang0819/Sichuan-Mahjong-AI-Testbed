package model;

import model.Tiles.Tile;
import model.Tiles.Tiles;

import java.util.List;
import java.util.Objects;

public class Player {
    private String name;
    private Tiles hand;

    public Player(String name, List<Tile> hand) {
        this.name = name;
        this.hand = new Tiles(hand);
    }

    public String getName() {
        return name;
    }

    public Tiles getHand() {
        return hand;
    }

    public void setHand(Tiles tiles) {
        this.hand = tiles;
    }


    public void addTile(Tile tile) {
        this.hand.add(tile);
    }


    public void plays(Tile tile) {
        this.hand.remove(tile);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '}';
    }
}

package model;

public class Tile {
    private String suit;
    private int number;

    public Tile(String suit, int number) {
        this.suit = suit;
        this.number = number;
    }

    public String getSuit() {
        return suit;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return suit + " " + number;
    }
}


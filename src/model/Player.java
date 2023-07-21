package model;

import java.util.List;

public class Player {
    private String name;
    private List<Tile> hand;

    public Player(String name, List<Tile> hand) {
        this.name = name;
        this.hand = hand;
        this.sortHands();
    }

    public void sortHands() {
        List<Tile> tiles = this.hand;
        tiles.sort((tile1, tile2) -> {
            if (tile1.getCategory().equals(tile2.getCategory())) {
                return tile1.getNumber() - tile2.getNumber();
            } else {
                return tile1.getCategory().compareTo(tile2.getCategory());
            }
        });
        this.setHand(tiles);
    }

    public List<Tile> getHand() {
        return hand;
    }

    private void setHand(List<Tile> hand) {
        for (int i = 0; i < hand.size(); i++) {
            Tile tile = hand.get(i);
            tile.setIndex(i);

            int padding = 10 * i;
            tile.setPosition(i, padding);
        }
        this.hand = hand;
    }

    public void addTile(Tile tile) {
        this.hand.add(tile);
        this.sortHands();
    }

    private void removeTile(Tile tile) {
        for (Tile t : this.hand) {
            if (t.equals(tile)) {
                this.hand.remove(t);
                break;
            }
        }
        this.sortHands();
    }

    public void plays(Tile tile) {
        this.removeTile(tile);
        this.sortHands();
    }
}

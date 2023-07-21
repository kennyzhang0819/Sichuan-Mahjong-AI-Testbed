package application;

import model.Player;
import model.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
    private final List<Tile> tiles;
    private final List<Player> players;
    private final List<Tile> table;

    public Game() {
        this.tiles = new ArrayList<>();
        this.table = new ArrayList<>();
        players = new ArrayList<Player>() {{
            add(new Player("Player", new ArrayList<>()));
            add(new Player("AI 1", new ArrayList<>()));
            add(new Player("AI 2", new ArrayList<>()));
            add(new Player("AI 3", new ArrayList<>()));
        }};

        String[] categories = {"Bamboo", "Character", "Dot"};
        for (String category : categories) {
            for (int i = 1; i <= 9; i++) {
                for (int j = 0; j < 4; j++) {
                    this.tiles.add(new Tile(category, i));
                }
            }
        }
        assert (this.tiles.size() == 108);
    }

    public void shuffleTiles() {
        Collections.shuffle(tiles);
    }

    public void deal() {
        for (Player player : players) {
            for (int i = 0; i < 13; i++) {
                Tile nextTile = this.getNextTile();
                player.getHand().add(nextTile);
            }
            player.sortHands();
        }
        int random = new Random().nextInt(4);
        Player player = players.get(random);
        player.addTile(this.getNextTile());
    }

    public void update(Tile tile) {
        this.table.add(tile);
    }


    // getters
    public Tile getNextTile() {
        if (tiles.size() == 0) {
            System.out.println("No more tiles");
            return null;
        }
        return tiles.remove(0);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Tile> getTable() {
        return table;
    }
}

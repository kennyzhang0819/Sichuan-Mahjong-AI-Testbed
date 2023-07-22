package application.core;

import model.log.Log;
import model.players.ai.AI1;
import model.players.Player;
import model.players.ai.AI2;
import model.players.ai.AI3;
import model.tiles.Tile;
import model.tiles.HandTiles;

import java.util.*;

public class Game {
    private final List<Tile> tiles;
    private final List<Player> players;
    private GameTurn gameTurn;
    private Log log;

    public Game() {
        this.log = new Log();
        this.tiles = new ArrayList<>();
        players = new ArrayList<Player>() {{
            add(new Player("Player", new ArrayList<>()));
            add(new AI1("AI1", new ArrayList<>()));
            add(new AI2("AI2", new ArrayList<>()));
            add(new AI3("AI3", new ArrayList<>()));
        }};
        log.addMessage("Players created");
        String[] categories = {"Bamboo", "Character", "Dot"};
        for (String category : categories) {
            for (int i = 1; i <= 9; i++) {
                for (int j = 0; j < 4; j++) {
                    this.tiles.add(new Tile(category, i));
                }
            }
        }
        log.addMessage("Tiles created");
        Collections.shuffle(tiles);
        log.addMessage("Tiles shuffled");
        this.deal();
        while (!Objects.equals(this.gameTurn.peek().getName(), "Player")) {
            this.next();
        }
        log.addMessage("AIs played their first turn");
    }

    public void deal() {
        for (Player player : players) {
            List<Tile> hand = new ArrayList<>();
            for (int i = 0; i < 13; i++) {
                Tile nextTile = this.getNextTile();
                hand.add(nextTile);
            }
            player.setHand(new HandTiles(hand));
        }
        int random = new Random().nextInt(this.players.size());
        Player player = players.get(random);
        player.addTile(this.getNextTile());
        log.addMessage("Tiles dealt");
        log.addMessage(player.getName() + " starts the game");
        this.gameTurn = new GameTurn(players, player);
    }

    public RoundData next() {
        Player turnPlayer = this.players.get(this.gameTurn.next());
        log.addMessage(turnPlayer.getName() + "'s turn");
        if (gameTurn.getRound() != 1) {
            turnPlayer.addTile(this.getNextTile());
        }
        turnPlayer.action();
        RoundData data = this.getRoundData(turnPlayer);
        log.addMessage(turnPlayer.getName() + " played, with " + data.getPlayerHand().size() + " tiles on hand");
        return data;
    }

    private RoundData getRoundData(Player turnPlayer) {
        Map<String, List<Tile>> output = new HashMap<>();
        for (Player player : this.players) {
            output.put(player.getName() + "Hand", player.getHand().toList());
            output.put(player.getName() + "Table", player.getTable().toList());
        }
        List<Tile> playerHand = output.get("PlayerHand");
        List<Tile> playerTable = output.get("PlayerTable");
        List<Tile> ai1Table = output.get("AI1Table");
        List<Tile> ai2Table = output.get("AI2Table");
        List<Tile> ai3Table = output.get("AI3Table");
        return new RoundData(turnPlayer, gameTurn.getRound(), playerHand, playerTable, ai1Table, ai2Table, ai3Table);
    }


    // getters
    public Tile getNextTile() {
        if (tiles.size() == 0) {
            log.addMessage("No more tiles");
            System.out.println("No more tiles");
            return null;
        }
        return this.tiles.remove(0);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Log getLog() {
        return log;
    }
}

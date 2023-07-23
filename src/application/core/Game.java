package application.core;

import model.OutputData;
import model.basic.TileTypeEnum;
import model.log.Log;
import model.players.AI1;
import model.players.Player;
import model.players.AI2;
import model.players.AI3;
import model.basic.Tile;
import model.tiles.HandTiles;

import java.util.*;

public class Game {
    private final List<Tile> tiles;
    private final List<Tile> allTiles;
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
        TileTypeEnum[] categories = {TileTypeEnum.B,
                TileTypeEnum.C, TileTypeEnum.D};
        for (TileTypeEnum category : categories) {
            for (int i = 1; i <= 9; i++) {
                for (int j = 0; j < 4; j++) {
                    this.tiles.add(new Tile(category, i));
                }
            }
        }
        this.allTiles = new ArrayList<>(this.tiles);
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

    public OutputData next() {
        Player turnPlayer = this.players.get(this.gameTurn.next());
        log.addMessage(turnPlayer.getName() + "'s turn");
        if (gameTurn.getRound() != 1) {
            turnPlayer.addTile(this.getNextTile());
        }
        turnPlayer.action();
        OutputData data = this.getRoundData(turnPlayer);
        log.addMessage(turnPlayer.getName() + " played, with " + data.getPlayerHand().size() + " tiles on hand");
        return data;
    }

    //Data Packaging
    private OutputData getRoundData(Player turnPlayer) {
        Map<String, List<Tile>> output = new HashMap<>();
        for (Player player : this.players) {
            output.put(player.getName() + "Hand", player.getHand().toList());
            output.put(player.getName() + "Table", player.getTable().toList());
        }
        List<Tile> playerHandList = output.get("PlayerHand");
        List<Tile> playerTable = output.get("PlayerTable");
        List<Tile> ai1Table = output.get("AI1Table");
        List<Tile> ai2Table = output.get("AI2Table");
        List<Tile> ai3Table = output.get("AI3Table");

        HandTiles playerHand = this.players.get(0).getHand();
        List<Tile> kong = new ArrayList<>();
        playerHand.getKong().forEach(group -> kong.addAll(group.toList()));
        List<Tile> pung = new ArrayList<>();
        playerHand.getPung().forEach(group -> pung.addAll(group.toList()));
        Tile newTile = playerHand.getNewTile();

        return new OutputData(turnPlayer, gameTurn.getRound(), playerHandList,
                kong, pung, newTile, playerTable, ai1Table, ai2Table, ai3Table);
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

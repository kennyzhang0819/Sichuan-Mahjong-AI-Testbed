package application.core;

import application.core.validation.PlayerStatusChecker;
import model.GameState;
import model.basic.TileTypeEnum;
import model.log.Log;
import model.players.*;
import model.basic.Tile;
import model.tiles.HandTiles;

import java.util.*;

public class Game {
    private boolean ended;
    private final List<Tile> tiles;
    private final List<Tile> allTiles;
    private final List<Player> players;
    private final Player player;
    private Player turnPlayer;
    private GameTurn gameTurn;
    private final Log log;
    private int leftOverRounds;

    public Game() {
        this.ended = false;
        this.log = new Log();
        this.tiles = new ArrayList<>();
        players = new ArrayList<Player>() {{
            add(new Player("Player", new ArrayList<>()));
            add(new AI1("AI1", new ArrayList<>()));
            add(new AI2("AI2", new ArrayList<>()));
            add(new AI3("AI3", new ArrayList<>()));
        }};
        this.player = players.get(0);
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
        Collections.shuffle(tiles);
        log.addMessage("Tiles created and shuffled");
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
        player.setPlayingStatus();

        log.addMessage("Tiles dealt");
        log.addMessage(player.getName() + " starts the game");
        this.gameTurn = new GameTurn(players, player);
    }

    public GameState next() {
       this.turnPlayer = this.players.get(gameTurn.next());
        if (turnPlayer.getStatus().contains(PlayerStatusEnum.HU)) {
            this.ended = true;
        }
        log.addMessage(turnPlayer.getName() + "'s turn");
        if (gameTurn.getRound() != 1) {
            turnPlayer.addTile(this.getNextTile());
            new PlayerStatusChecker(turnPlayer, turnPlayer.getHand().getNewTile());
        }
        if (turnPlayer == this.player) {
            log.addMessage("directing to " + turnPlayer.getName() + " for action");
            return this.getGameState();
        } else {
            turnPlayer.setPlayingStatus();
            turnPlayer.action();
            List<Player> next3Players = new ArrayList<Player>() {{
                for (int i = 0; i < 3; i++) {
                    add(gameTurn.peek());
                }
            }};
            for (Player player : next3Players) {
                new PlayerStatusChecker(player, turnPlayer.getHand().getLast());
            }
            log.addMessage(turnPlayer.getName() + " played");
            turnPlayer.setWaitingStatus();
            gameTurn.peek().setPlayingStatus();
            return this.getGameState();
        }
    }

    public GameState processPung(Player player) {
        player.getHand().addPung(this.turnPlayer.getTable().getLast());
        this.turnPlayer.getTable().removeLast();
        this.gameTurn = new GameTurn(this.players, player);
        return this.getGameState();
    }

    public GameState processPlayerSkipped() {
        return this.playLeftOverRounds();
    }

    public GameState processPlayerPlayed() {
        player.setWaitingStatus();
        this.leftOverRounds = 5;
        return this.playLeftOverRounds();
    }

    public GameState playLeftOverRounds() {
        for (int i = 0; i < this.leftOverRounds; i++) {
            this.next();
            leftOverRounds--;
            System.out.println(leftOverRounds);
            if (this.player.getStatus().contains(PlayerStatusEnum.CHOW) ||
                    this.player.getStatus().contains(PlayerStatusEnum.PUNG) ||
                    this.player.getStatus().contains(PlayerStatusEnum.KONG)) {
                log.addMessage("press c to chow, p to pung, k to kong, or s to skip");
                return this.getGameState();
            }
        }
        return this.getGameState();
    }


    // getters
    public GameState getGameState() {
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

        HandTiles playerHand = this.player.getHand();
        List<Tile> kong = new ArrayList<>();
        playerHand.getKong().forEach(group -> kong.addAll(group.toList()));
        List<Tile> pung = new ArrayList<>();
        playerHand.getPung().forEach(group -> pung.addAll(group.toList()));
        Tile newTile = playerHand.getNewTile();

        return new GameState(this.turnPlayer, this.players, gameTurn.getRound(), playerHandList,
                kong, pung, newTile, playerTable, ai1Table, ai2Table, ai3Table);
    }

    public Tile getNextTile() {
        if (tiles.size() == 0) {
            log.addMessage("No more tiles");
            this.ended = true;
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

    public boolean isOver() {
        return ended;
    }


}

package application.game;

import application.game.validation.PlayerStatusChecker;
import model.GameState;
import model.basic.Tile;
import model.basic.TileTypeEnum;
import model.log.Log;
import model.players.*;
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
    private boolean ongoingPung;

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
        this.unfairDeal(); //changeback to deal() after testing
        while (!Objects.equals(this.gameTurn.peek().getName(), "Player")) {
            this.next();
        }
        this.next();
        log.addMessage("AIs played their first turn");
    }

    private void deal() {
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

    private void unfairDeal() {
        this.player.setHand(new HandTiles(new ArrayList<Tile>() {{
            add(new Tile(TileTypeEnum.B, 2));
            add(new Tile(TileTypeEnum.B, 3));
            add(new Tile(TileTypeEnum.B, 4));
            add(new Tile(TileTypeEnum.B, 5));
            add(new Tile(TileTypeEnum.B, 6));
            add(new Tile(TileTypeEnum.C, 8));
            add(new Tile(TileTypeEnum.C, 8));
            add(new Tile(TileTypeEnum.C, 8));
            add(new Tile(TileTypeEnum.C, 9));
            add(new Tile(TileTypeEnum.C, 9));
            add(new Tile(TileTypeEnum.C, 9));
            add(new Tile(TileTypeEnum.D, 6));
            add(new Tile(TileTypeEnum.D, 6));

        }}));
        for (Player player : players) {
            if (player.getName().equals("Player")) {
                continue;
            }
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

    private Tile getNextTile() {
        if (tiles.size() == 0) {
            log.addMessage("No more tiles");
            this.ended = true;
            return null;
        }
        return this.tiles.remove(0);
    }

    private void next() {
        this.turnPlayer = gameTurn.next();
        if (turnPlayer.getStatus().contains(PlayerStatusEnum.HU)) {
            this.ended = true;
        }
        log.addMessage(turnPlayer.getName() + "'s turn");
        if (gameTurn.getRound() != 1) {
            turnPlayer.addTile(this.getNextTile());
        }
        new PlayerStatusChecker(turnPlayer, turnPlayer.getHand().getNewTile());
        if (turnPlayer == this.player) {
            log.addMessage("directing to " + turnPlayer.getName() + " for action");
        } else {
            turnPlayer.setPlayingStatus();
            turnPlayer.action();
            this.processPlayed();
        }
    }

    private void processPlayed() {
        this.ongoingPung = false;
        List<Player> next3Players = this.gameTurn.peek3();
        for (Player player : next3Players) {
            new PlayerStatusChecker(player, this.turnPlayer.getTable().getLast());
        }
        log.addMessage(this.turnPlayer.getName() + " played");
        this.turnPlayer.setWaitingStatus();
        if (!this.player.containsChouPungKong()) {
            this.gameTurn.getPlayerAfter(this.turnPlayer).setPlayingStatus();
        }
    }

    private void playLeftOverRounds() {
        for (int i = 0; i < this.leftOverRounds; ) {
            this.next();
            leftOverRounds--;
            if (this.player.containsChouPungKong()) {
                log.addMessage("press c to chow, p to pung, k to kong, or s to skip");
                this.getGameState();
                return;
            }
        }
        this.getGameState();
    }


    //PUBLIC METHODS
    public void processPlayerPlayed() {
        this.processPlayed();
        this.leftOverRounds = 4;
        this.playLeftOverRounds();
    }

    public void processPung(Player player) {
        player.getHand().addPung(this.turnPlayer.getTable().getLast());
        this.turnPlayer.getTable().removeLast();
        this.gameTurn = new GameTurn(this.players, player);
        this.turnPlayer = gameTurn.next();
        player.setPlayingStatus();
        player.clearStatus();
        this.ongoingPung = true;
        log.addMessage(player.getName() + " pung, now " + player.getName() + " will play 1 tile");
    }

    public void processKong(Player player) {
        if (player.getStatus().contains(PlayerStatusEnum.NORMAL_KONG)) {
            player.getHand().addNormalKong(this.turnPlayer.getTable().getLast());
            this.turnPlayer.getTable().removeLast();
        } else if (player.getStatus().contains(PlayerStatusEnum.ADD_KONG)) {
            player.getHand().addAddKong(this.turnPlayer.getTable().getLast());
        } else if (player.getStatus().contains(PlayerStatusEnum.HIDDEN_KONG)) {
            player.getHand().addHiddenKong(this.turnPlayer.getTable().getLast());
        } else {
            throw new RuntimeException("Invalid kong");
        }
        player.addTile(this.getNextTile());
        this.gameTurn = new GameTurn(this.players, this.player);
        this.turnPlayer = gameTurn.next();
        player.setPlayingStatus();
        player.clearStatus();
        this.ongoingPung = true;
        log.addMessage(player.getName() + " kong, now " + player.getName() + " will play 1 tile");
    }

    public void processPlayerSkipped() {
        gameTurn.getPlayerAfter(turnPlayer).setPlayingStatus();
        this.playLeftOverRounds();
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
                kong, pung, newTile, playerTable, ai1Table, ai2Table, ai3Table, this.ongoingPung);
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

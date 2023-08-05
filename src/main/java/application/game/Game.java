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
    private final List<Player> players;
    private final Player player;
    private Player turnPlayer;
    private GameTurn gameTurn;
    private final Log log;

    public Game() {
        this.ended = false;
        this.log = new Log();
        this.tiles = new ArrayList<>();
        players = new ArrayList<Player>() {{
            add(new Player("player", new ArrayList<>(), 0));
            add(new AI1("ai1", new ArrayList<>(), 1));
            add(new AI2("ai2", new ArrayList<>(), 2));
            add(new AI3("ai3", new ArrayList<>(), 3));
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
        Collections.shuffle(tiles);
        log.addMessage("Tiles created and shuffled");
        this.unfairDeal(); //changeback to deal() after testing
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
        this.players.get(1).setHand(new HandTiles(new ArrayList<Tile>() {{
            add(new Tile(TileTypeEnum.C, 1));
            add(new Tile(TileTypeEnum.C, 1));
            add(new Tile(TileTypeEnum.C, 2));
            add(new Tile(TileTypeEnum.C, 2));
            add(new Tile(TileTypeEnum.B, 3));
            add(new Tile(TileTypeEnum.B, 3));
            add(new Tile(TileTypeEnum.C, 4));
            add(new Tile(TileTypeEnum.C, 5));
            add(new Tile(TileTypeEnum.B, 6));
            add(new Tile(TileTypeEnum.B, 6));
            add(new Tile(TileTypeEnum.C, 7));

        }}));
        for (Player player : players) {
            if (player.getName().equals(this.players.get(1).getName())) {
                continue;
            }
            List<Tile> hand = new ArrayList<>();
            for (int i = 0; i < 13; i++) {
                Tile nextTile = this.getNextTile();
                hand.add(nextTile);
            }
            player.setHand(new HandTiles(hand));
        }
        Player player = players.get(0);
        player.addTile(new Tile(TileTypeEnum.B, 2));
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
        this.turnPlayer.setPlayingStatus();
        new PlayerStatusChecker(turnPlayer, turnPlayer.getHand().getNewTile());
        this.action();
    }

    private void action() {
        if (turnPlayer == this.player) {
            log.addMessage("directing to " + turnPlayer.getName() + " for action");
        } else {
            turnPlayer.playAction();
            this.processPlayed();
        }
    }

    public void processPlayed() {
        List<Player> next3Players = this.gameTurn.peek3();
        Tile lastPlayedTile = this.turnPlayer.getTable().getLast();
        for (Player player : next3Players) {
            new PlayerStatusChecker(player, lastPlayedTile);
        }
        log.addMessage(this.turnPlayer.getName() + " played " + this.turnPlayer.getTable().getLast());
        this.turnPlayer.setWaitingStatus();

        for (Player p : this.players) {
            if (p.containsChouPungKong() && p == this.player) {
                log.addMessage("For " + lastPlayedTile + ", press c to chow, p to pung, k to kong, or s to skip");
                return;
            } else if (p.containsChouPungKong()) {
                if (p.otherAction(lastPlayedTile) == PlayerActionEnum.CHOW) {
                    this.processChou(p);
                    return;
                } else if (p.otherAction(lastPlayedTile) == PlayerActionEnum.PUNG) {
                    this.processPung(p);
                    return;
                } else if (p.otherAction(lastPlayedTile) == PlayerActionEnum.KONG) {
                    this.processKong(p);
                    return;
                } else if (p.otherAction(lastPlayedTile) == PlayerActionEnum.SKIP) {
                    this.processSkip(p);
                    return;
                }
            }
        }
        this.next();
    }

    public void processHu(Player player) {
        log.addMessage(player.getName() + " wins");
        System.out.println(player.getHand());
    }

    public void processChou(Player player) {
        player.setHuStatus();
        this.turnPlayer.getTable().removeLast();
        log.addMessage(player.getName() + " chou");
        this.processHu(player);
    }

    public void processPung(Player player) {
        player.getHand().addPung(this.turnPlayer.getTable().getLast());
        this.turnPlayer.getTable().removeLast();
        this.gameTurn = new GameTurn(this.players, player);
        this.turnPlayer = gameTurn.next();
        this.turnPlayer.setPlayingStatus();
        this.turnPlayer.clearStatus();
        log.addMessage(this.turnPlayer.getName() + " pung, now play 1 tile");
        this.action();
    }

    public void processKong(Player player) {
        if (player.getStatus().contains(PlayerStatusEnum.NORMAL_KONG)) {
            player.getHand().addNormalKong(this.turnPlayer.getTable().getLast());
            this.turnPlayer.getTable().removeLast();
        } else if (player.getStatus().contains(PlayerStatusEnum.ADD_KONG)) {
            player.getHand().addAddKong();
        } else if (player.getStatus().contains(PlayerStatusEnum.HIDDEN_KONG)) {
            player.getHand().addHiddenKong();
        } else {
            throw new RuntimeException("Invalid kong");
        }
        player.addTile(this.getNextTile());
        player.setPlayingStatus();
        new PlayerStatusChecker(player, player.getHand().getNewTile());
        if (this.player.containsChouPungKong()) {
            log.addMessage("press k to kong or s to skip");
            return;
        }
        this.gameTurn = new GameTurn(this.players, player);
        this.turnPlayer = gameTurn.next();
        this.turnPlayer.setPlayingStatus();
        this.turnPlayer.clearStatus();
        log.addMessage(this.turnPlayer.getName() + " kong, now play 1 tile");
        this.action();
    }

    public void processSkip(Player player) {
        player.clearStatus();
        gameTurn.getPlayerAfter(turnPlayer).setPlayingStatus();
        log.addMessage(player.getName() + " skipped");
        this.next();
    }


    // getters
    public GameState getGameState() {
        HandTiles playerHand = this.players.get(0).getHand();
        HandTiles ai1Hand = this.players.get(1).getHand();
        HandTiles ai2Hand = this.players.get(2).getHand();
        HandTiles ai3Hand = this.players.get(3).getHand();

        List<Tile> playerHandList = playerHand.toList();
        List<Tile> ai1HandList = ai1Hand.toList();
        List<Tile> ai2HandList = ai2Hand.toList();
        List<Tile> ai3HandList = ai3Hand.toList();

        List<Tile> playerTable = this.players.get(0).getTable().toList();
        List<Tile> ai1Table = this.players.get(1).getTable().toList();
        List<Tile> ai2Table = this.players.get(2).getTable().toList();
        List<Tile> ai3Table = this.players.get(3).getTable().toList();

        List<Tile> kong = new ArrayList<>();
        playerHand.getKong().forEach(group -> kong.addAll(group.toList()));
        List<Tile> pung = new ArrayList<>();
        playerHand.getPung().forEach(group -> pung.addAll(group.toList()));
        Tile newTile = playerHand.getNewTile();

        return new GameState(this.turnPlayer, this.players, gameTurn.getRound(), playerHandList,
                kong, pung, newTile, playerTable, ai1Table, ai2Table, ai3Table);
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

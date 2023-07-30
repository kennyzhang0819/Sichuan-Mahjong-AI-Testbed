package model.players;

import model.tiles.HandTiles;
import model.tiles.PlayerTableTiles;
import model.basic.Tile;
import model.tiles.Tiles;
import utils.TileUtils;

import java.util.*;

public class Player {
    protected Set<PlayerStatusEnum> status;
    protected final String name;
    protected HandTiles hand;
    private final PlayerTableTiles table;

    public Player(String name, List<Tile> hand) {
        this.name = name;
        this.hand = new HandTiles(hand);
        this.table = new PlayerTableTiles();
        this.status = new HashSet<PlayerStatusEnum>() {{
            add(PlayerStatusEnum.WAITING);
        }};
    }

    public void plays(Tile tile) {
        this.hand.remove(tile);
        TileUtils.resetEntity(tile);
        this.table.add(tile);
    }

    public String getName() {
        return name;
    }

    public HandTiles getHand() {
        return hand;
    }

    public Tiles getTable() {
        return table;
    }

    public void setHand(HandTiles tiles) {
        this.hand = tiles;
    }

    public Set<PlayerStatusEnum> getStatus() {
        return status;
    }

    public boolean isPlaying() {
        return this.status.contains(PlayerStatusEnum.PLAYING);
    }

    public boolean isWaiting() {
        return this.status.contains(PlayerStatusEnum.WAITING);
    }

    public boolean containsChou() {
        return this.status.contains(PlayerStatusEnum.CHOW);
    }

    public boolean containsPung() {
        return this.status.contains(PlayerStatusEnum.PUNG);
    }

    public boolean containsKong() {
        return this.status.contains(PlayerStatusEnum.NORMAL_KONG)
                || this.status.contains(PlayerStatusEnum.HIDDEN_KONG)
                || this.status.contains(PlayerStatusEnum.ADD_KONG);
    }

    public boolean containsChouPungKong() {
        return this.status.contains(PlayerStatusEnum.CHOW)
                || this.status.contains(PlayerStatusEnum.PUNG)
                || this.containsKong();
    }

    //Status Setters
    public void setPlayingStatus() {
        if (this.status.contains(PlayerStatusEnum.WAITING)) {
            this.status.remove(PlayerStatusEnum.WAITING);
            this.status.add(PlayerStatusEnum.PLAYING);
        }
    }

    public void setWaitingStatus() {
        if (this.status.contains(PlayerStatusEnum.PLAYING)) {
            this.status.remove(PlayerStatusEnum.PLAYING);
            this.status.add(PlayerStatusEnum.WAITING);
        }
    }

    public void setHuStatus() {
        this.status.add(PlayerStatusEnum.HU);
    }

    public void setNormalKongStatus() {
        this.status.add(PlayerStatusEnum.NORMAL_KONG);
    }

    public void setAddKongStatus() {
        this.status.add(PlayerStatusEnum.ADD_KONG);
    }

    public void setHiddenKongStatus() {
        this.status.add(PlayerStatusEnum.HIDDEN_KONG);
    }

    public void setPungStatus() {
        this.status.add(PlayerStatusEnum.PUNG);
    }

    public void setChowStatus() {
        this.status.add(PlayerStatusEnum.CHOW);
    }

    public void clearKongStatus() {
        this.status.remove(PlayerStatusEnum.NORMAL_KONG);
        this.status.remove(PlayerStatusEnum.ADD_KONG);
        this.status.remove(PlayerStatusEnum.HIDDEN_KONG);
    }

    public void clearPungStatus() {
        this.status.remove(PlayerStatusEnum.PUNG);
    }

    public void clearChowStatus() {
        this.status.remove(PlayerStatusEnum.CHOW);
    }

    public void clearStatus() {
        this.clearChowStatus();
        this.clearKongStatus();
        this.clearPungStatus();
    }

    public void addTile(Tile tile) {
        this.hand.add(tile);
    }

    public void action() {

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
                "status=" + status +
                ", name='" + name + '\'' +
                ", hand=" + hand +
                '}';
    }


}

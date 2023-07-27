package application.game.validation;

import model.basic.Tile;
import model.players.Player;
import model.tiles.Group;
import model.tiles.GroupEnum;

import java.util.*;

public class PlayerStatusChecker {
    private Player player;
    private List<Tile> tiles;
    private final Map<String, List<Tile>> categorizedTiles;
    private Set<Group> pair;
    private Set<Group> sequence;
    private Set<Group> triple;
    private Set<Group> pung;
    private Set<Group> kong;
    private PungKongChecker pungKongChecker;

    public PlayerStatusChecker(Player player, Tile newTile) {
        this.pair = new HashSet<>();
        this.sequence = new HashSet<>();
        this.triple = new HashSet<>();
        this.pung = new HashSet<>();
        this.kong = new HashSet<>();
        this.categorizedTiles = new HashMap<>();
        this.player = player;
        this.pungKongChecker = new PungKongChecker(player, newTile);
        List<Tile> tiles = player.getHand().toList();
        tiles.add(newTile);
        this.setTiles(tiles);
    }

    private void setTiles(List<Tile> tiles) {
        this.pung.addAll(player.getHand().getPung());
        this.kong.addAll(player.getHand().getKong());
        this.tiles = new ArrayList<>(tiles);
        this.tiles.sort(Comparator.comparing(Tile::getType).thenComparing(Tile::getNumber));
        for (Tile tile : this.tiles) {
            this.categorizedTiles.computeIfAbsent(tile.getType().getEnglish(), k -> new ArrayList<>()).add(tile);
        }
        for (List<Tile> tileByCategory : this.categorizedTiles.values()) {
            this.generateSets(tileByCategory);
        }
        this.updateStatus();
    }

    public void updateStatus() {
        this.player.clearStatus();
        if (this.checkHu()) {
            if (this.player.isPlaying()) {
                this.player.setHuStatus();
            }
            this.player.setChowStatus();
        }
        if (this.pungKongChecker.canPung()) {
            this.player.setPungStatus();
            System.out.println("setted pung status to " + this.player.getName());
        }
        if (this.pungKongChecker.canNormalKong()) {
            this.player.setNormalKongStatus();
            System.out.println("setted Normal Kong status to " + this.player.getName());
        }
        if (this.pungKongChecker.canHiddenKong()) {
            this.player.setHiddenKongStatus();
            System.out.println("setted Hidden Kong status to " + this.player.getName());
        }
        if (this.pungKongChecker.canAddKong()) {
            this.player.setAddKongStatus();
            System.out.println("setted Add Kong status to " + this.player.getName());
        }
    }

    private boolean checkHu() {
        HuFitter huFitter = new HuFitter(this.pair, this.sequence, this.triple, this.pung, this.kong);
        Set<List<Group>> candidates = huFitter.fitAllHu();
        for (List<Group> groups : candidates) {
            if (this.canHuFromHand(this.tiles, groups)) {
                return true;
            }
        }
        return false;
    }

    private void generateSets(List<Tile> tiles) {
        if (tiles.size() < 2) {
            return;
        }
        if (tiles.size() == 2) {
            this.setGroups(tiles.get(0), tiles.get(1));
        }
        for (int i = 0; i < tiles.size() - 2; i++) {
            for (int j = i + 1; j < tiles.size() - 1; j++) {
                for (int k = j + 1; k < tiles.size(); k++) {
                    this.setGroups(tiles.get(i), tiles.get(j), tiles.get(k));
                }
            }
        }
    }

    private void setGroups(Tile... tiles) {
        List<Tile> tilesList = Arrays.asList(tiles);
        for (int i = 0; i < tilesList.size(); i++) {
            for (int j = i + 1; j < tilesList.size(); j++) {
                if (tilesList.get(i).getNumber() == tilesList.get(j).getNumber()) {
                    this.pair.add(new Group(Arrays.asList(tilesList.get(i), tilesList.get(j)), GroupEnum.PAIR, 1));
                }
            }
        }
        for (int i = 0; i < tilesList.size() - 2; i++) {
            if (tilesList.get(i).getNumber() == tilesList.get(i + 1).getNumber()
                    && tilesList.get(i + 1).getNumber() == tilesList.get(i + 2).getNumber()) {
                this.triple.add(new Group(Arrays.asList(tilesList.get(i), tilesList.get(i + 1), tilesList.get(i + 2)), GroupEnum.TRIPLE, 1));
            } else if (tilesList.get(i + 1).getNumber() == tilesList.get(i).getNumber() + 1
                    && tilesList.get(i + 2).getNumber() == tilesList.get(i + 1).getNumber() + 1) {
                this.sequence.add(new Group(Arrays.asList(tilesList.get(i), tilesList.get(i + 1), tilesList.get(i + 2)), GroupEnum.SEQUENCE, 1));
            }
        }
    }

    private boolean canHuFromHand(List<Tile> hand, Collection<Group> groups) {
        List<Tile> handCopy = new ArrayList<>(hand);
        for (Group group : groups) {
            for (Tile tile : group.toList()) {
                if (!handCopy.remove(tile)) {
                    return false;
                }
            }
        }
        return handCopy.isEmpty();
    }

}

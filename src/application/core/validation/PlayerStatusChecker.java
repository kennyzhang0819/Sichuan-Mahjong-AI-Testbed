package application.core.validation;

import model.players.Player;
import model.players.PlayerStatusEnum;
import model.tiles.Group;
import model.tiles.GroupEnum;
import model.tiles.HandTiles;
import model.basic.Tile;

import java.util.*;

public class PlayerStatusChecker {
    private Player player;
    private List<Tile> tiles;
    private Map<String, List<Tile>> categorizedTiles;
    private Set<Group> pair;
    private Set<Group> sequence;
    private Set<Group> triple;
    private Set<Group> pung;
    private Set<Group> kong;

    public PlayerStatusChecker(Player player) {
        this.player = player;
        HandTiles tiles = player.getHand();
        assert (tiles.getClass() == HandTiles.class);
        this.setTiles(tiles.toList());
    }

    public void setTiles(List<Tile> tiles) {
        this.pair = new HashSet<>();
        this.sequence = new HashSet<>();
        this.triple = new HashSet<>();
        this.pung = new HashSet<>();
        this.kong = new HashSet<>();
        this.categorizedTiles = new HashMap<>();
        this.tiles = tiles;
        this.tiles.sort(Comparator.comparing(Tile::getType).thenComparing(Tile::getNumber));
        for (Tile tile : this.tiles) {
            this.categorizedTiles.computeIfAbsent(tile.getType().getEnglish(), k -> new ArrayList<>()).add(tile);
        }
        for (List<Tile> tileByCategory : this.categorizedTiles.values()) {
            this.generateSets(tileByCategory);
        }
    }

    public void updateStatus() {
        if (this.checkHu()) {
            this.player.setStatus(PlayerStatusEnum.HU);
        }
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

    public boolean checkHu() {
        if (categorizedTiles.keySet().size() > 2) {
            return false;
        }
        HuFitter huFitter = new HuFitter(this.pair, this.sequence, this.triple, this.pung, this.kong);
        Set<List<Group>> candidates = huFitter.fitAllHu();
        for (List<Group> groups : candidates) {
            if (this.canHuFromHand(this.tiles, groups)) {
                return true;
            }
        }
        return false;
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

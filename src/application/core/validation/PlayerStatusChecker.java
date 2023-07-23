package application.core.validation;

import model.players.Player;
import model.tiles.Group;
import model.tiles.GroupEnum;
import model.tiles.HandTiles;
import model.basic.Tile;

import java.util.*;

public class PlayerStatusChecker {
    private final List<Tile> tiles;
    private final Map<String, List<Tile>> categorizedTiles;
    private final Set<Group> pair;
    private final Set<Group> sequence;
    private final Set<Group> triple;
    private final Set<Group> pung;
    private final Set<Group> kong;

    public PlayerStatusChecker(Player player) {
        HandTiles tiles = player.getHand();
        assert (tiles.getClass() == HandTiles.class);
        this.tiles = tiles.toList();
        this.pair = new HashSet<>();
        this.sequence = new HashSet<>();
        this.triple = new HashSet<>();
        this.pung = new HashSet<>();
        this.kong = new HashSet<>();

        this.tiles.sort(Comparator.comparing(Tile::getCategory).thenComparing(Tile::getNumber));
        this.categorizedTiles = new HashMap<>();
        for (Tile tile : this.tiles) {
            this.categorizedTiles.computeIfAbsent(tile.getCategory().getEnglish(), k -> new ArrayList<>()).add(tile);
        }
        for (List<Tile> tileByCategory : this.categorizedTiles.values()) {
            this.generateSets(tileByCategory);
        }
    }

    public void generateSets(List<Tile> tiles) {
        if (tiles.size() < 3) {
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
                    this.pair.add(new Group(Arrays.asList(tilesList.get(i), tilesList.get(j)), GroupEnum.PAIR));
                }
            }
        }
        for (int i = 0; i < tilesList.size() - 2; i++) {
            if (tilesList.get(i).getNumber() == tilesList.get(i + 1).getNumber()
                    && tilesList.get(i + 1).getNumber() == tilesList.get(i + 2).getNumber()) {
                this.triple.add(new Group(Arrays.asList(tilesList.get(i), tilesList.get(i + 1), tilesList.get(i + 2)), GroupEnum.TRIPLE));
            } else if (tilesList.get(i + 1).getNumber() == tilesList.get(i).getNumber() + 1
                    && tilesList.get(i + 2).getNumber() == tilesList.get(i + 1).getNumber() + 1) {
                this.sequence.add(new Group(Arrays.asList(tilesList.get(i), tilesList.get(i + 1), tilesList.get(i + 2)), GroupEnum.SEQUENCE));
            }
        }
    }

    public boolean checkHu() {
        if (categorizedTiles.keySet().size() > 2) {
            return false;
        }
        HuFitter huFitter = new HuFitter(this.pair, this.sequence, this.triple, this.pung, this.kong);
        Set<List<Group>> candidates = huFitter.fitStandardHu();
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

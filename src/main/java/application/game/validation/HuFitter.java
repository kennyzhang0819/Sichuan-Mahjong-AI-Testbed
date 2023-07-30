package application.game.validation;

import model.basic.TileTypeEnum;
import model.tiles.Group;
import model.basic.Tile;
import utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class HuFitter {
    private final List<Group> allGroups;
    private final List<Group> allGroupsOf3;
    private final Set<Group> pair;
    private final Set<Group> sequence;
    private final Set<Group> sequenceDup;
    private final Set<Group> triple;
    private final Set<Group> pung;

    private final Set<Group> kong;

    public HuFitter(Set<Group> pair, Set<Group> sequence, Set<Group> triple, Set<Group> pung, Set<Group> kong) {
        this.pair = pair;
        this.sequence = sequence;
        this.triple = triple;
        this.pung = pung;
        this.kong = kong;

        this.sequenceDup = new HashSet<>();
        List<Group> sequenceDup = new ArrayList<>(this.sequence);
        for (Group group: sequenceDup) {
            this.sequenceDup.add(group.getDup());
        }
        this.allGroups = new ArrayList<>();
        this.allGroups.addAll(this.sequence);
        this.allGroups.addAll(this.triple);
        this.allGroups.addAll(this.pair);
        this.allGroups.addAll(this.kong);
        this.allGroupsOf3 = new ArrayList<>();
        this.allGroupsOf3.addAll(this.sequence);
        this.allGroupsOf3.addAll(this.sequenceDup);
        this.allGroupsOf3.addAll(this.triple);
    }

    public Set<List<Group>> fitAllHu() {
        List<Set<List<Group>>> listOfSets = Arrays.asList(
                this.fitStandardHu(),
                this.fitSevenPairsHu());
        Set<List<Group>> result = listOfSets.stream()
                .flatMap(set -> set.stream())
                .collect(Collectors.toSet());
        return result;
    }

    public Set<List<Group>> fitStandardHu() {
        int groupsNeeded = 4 - this.pung.size() - this.kong.size();
        Set<List<Group>> result = new HashSet<>();
        for (Group pair : this.pair) {
            Set<Set<Group>> combinations = Utils.getCombinations(this.allGroupsOf3, groupsNeeded);
            for (Set<Group> groupOf3 : combinations) {
                List<Group> temp = new ArrayList<>();
                temp.add(pair);
                temp.addAll(groupOf3);
                result.add(temp);
            }
        }
        return result;
    }

    public Set<List<Group>> fitPungHu() {
        Set<List<Group>> result = new HashSet<>();
        return null;
    }

    public Set<List<Group>> fitSevenPairsHu() {
        if (this.pair.size() != 7) {
            return new HashSet<>();
        }
        Set<List<Group>> result = new HashSet<>();
        if (this.kong.size() >= 3) {
            //Dragon7Pairs
            List<Group> temp = new ArrayList<>();
            temp.addAll(this.kong);
            if (this.kong.size() == 3) {
                temp.addAll(this.pair);
            }
            result.add(temp);
            return result;
        }
        if (this.isPure(new ArrayList<>(this.pair))) {
            //Pure7Pairs
            result.add(new ArrayList<>(this.pair));
            return result;
        }
        result.add(new ArrayList<>(this.pair));
        return result;
    }

    private boolean isPure(List<Group> groups) {
        TileTypeEnum category = groups.get(0).toList().get(0).getType();
        for (Group group : groups) {
            for (Tile tile : group.toList()) {
                if (!tile.getType().equals(category)) {
                    return false;
                }
            }
        }
        return true;
    }
}

package application.core.validation;

import model.basic.TileCategoryEnum;
import model.tiles.Group;
import model.basic.Tile;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HuFitter {
    private final List<Group> allGroups;
    private final List<Group> allGroupsOf3;
    private final Set<Group> pair;
    private final Set<Group> sequence;
    private final Set<Group> triple;
    private final Set<Group> pung;

    private final Set<Group> kong;

    public HuFitter(Set<Group> pair, Set<Group> sequence, Set<Group> triple, Set<Group> pung, Set<Group> kong) {
        this.pair = pair;
        this.sequence = sequence;
        this.triple = triple;
        this.pung = pung;
        this.kong = kong;
        Set<Group> allGroups = new HashSet<>();
        allGroups.addAll(this.sequence);
        allGroups.addAll(this.triple);
        allGroups.addAll(this.pair);
        allGroups.addAll(this.kong);
        this.allGroups = new ArrayList<>(allGroups);
        Set<Group> allGroupsOf3 = new HashSet<>();
        allGroupsOf3.addAll(this.sequence);
        allGroupsOf3.addAll(this.triple);
        this.allGroupsOf3 = new ArrayList<>(allGroupsOf3);
    }

    public Set<List<Group>> fitStandardHu() {
        Set<List<Group>> result = new HashSet<>();
        for (Group pair : this.pair) {
            for (Set<Group> groupOf3 : Utils.getCombinations(this.allGroupsOf3, 4)) {
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
        if (this.isPure((List<Group>) this.pair)) {
            //Pure7Pairs
            result.add(new ArrayList<>(this.pair));
            return result;
        }
        result.add(new ArrayList<>(this.pair));
        return result;
    }

    private boolean isPure(List<Group> groups) {
        TileCategoryEnum category = groups.get(0).toList().get(0).getCategory();
        for (Group group : groups) {
            for (Tile tile : group.toList()) {
                if (!tile.getCategory().equals(category)) {
                    return false;
                }
            }
        }
        return true;
    }
}

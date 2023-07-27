package utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {

    public static <T> Set<Set<T>> getCombinations(List<T> list, int n) {
        Set<Set<T>> result = new HashSet<>();

        getCombinationsHelper(list, n, 0, new HashSet<>(), result);

        return result;
    }

    private static  <T> void getCombinationsHelper(List<T> list, int n, int index, Set<T> current, Set<Set<T>> result) {
        if (current.size() == n) {
            result.add(new HashSet<>(current));
            return;
        }

        if (index == list.size()) {
            return;
        }

        T element = list.get(index);

        current.add(element);
        getCombinationsHelper(list, n, index + 1, current, result);

        current.remove(element);
        getCombinationsHelper(list, n, index + 1, current, result);
    }



}

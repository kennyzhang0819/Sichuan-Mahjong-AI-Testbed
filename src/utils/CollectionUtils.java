package utils;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils<T> {
    public static <T> List<T> join(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<>(list1);
        result.addAll(list2);
        return result;
    }
}


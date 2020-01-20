package org.game.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {
    public static <T> List<T> reverseList (List<T> list) {
        ArrayList newList = new ArrayList<>(list);
        Collections.reverse(newList);
        return newList;
    }
}

package org.deliveroo.cronparser.util;

import java.util.List;

public class Helper {
    public static String replaceWordsWithNumbers(List<String> baseList, String expression) {
        for (int index = 1; index < baseList.size(); index++) {
            expression = expression.replaceAll(baseList.get(index), String.valueOf(index));
        }
        return expression;
    }
}

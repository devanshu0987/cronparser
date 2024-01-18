package org.deliveroo.cronparser.util;

import java.util.List;

public final class Constant {

    private Constant() {
    }

    public static final String SLASH = "/";
    public static final String COMMA = ",";
    public static final String HYPHEN = "-";
    public static final String ASTERISK = "*";
    public static final String SPACE = " ";
    public static final Integer NO_OF_PARTS = 7;
    public static final String QUESTION_MARK = "?";

    public static List<String> monthList = List.of(
            "", "JAN", "FEB", "MAR",
            "APR", "MAY", "JUN",
            "JUL", "AUG", "SEP",
            "OCT", "NOV", "DEC");

    public static List<String> dayOfWeekList = List.of(
            "", "SUN", "MON", "TUE", "WED",
            "THU", "FRI", "SAT");


}

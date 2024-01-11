package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.model.CronFieldType;
import org.deliveroo.cronparser.model.Range;

import java.util.List;

import static org.deliveroo.cronparser.util.Constant.*;
import static org.deliveroo.cronparser.util.Constant.HYPHEN;

public class FieldParser {
    // This will serve as a common place to parse any cron field.
    public static CronField parseField(String value, CronFieldType type) {
        // VALIDATION
        if (value.isEmpty() || value.isBlank()) throw new IllegalArgumentException(type + " part should not be empty");

        CronField f = new CronField(value, type);
        // HANDLE COMMA
        String[] fields = value.split(COMMA);

        for (String field : fields) {
            if (type == CronFieldType.MINUTE)
                f.setRange(List.of(0, 15, 30, 45));

            if (type == CronFieldType.HOUR)
                f.setRange(List.of(0));

            if (type == CronFieldType.DAY_OF_MONTH)
                f.setRange(parseRange(field, type));

            if (type == CronFieldType.MONTH)
                f.setRange(parseRange(field, type));

            if (type == CronFieldType.DAY_OF_WEEK)
                f.setRange(List.of(1, 2, 3, 4, 5));
        }
        return f;
    }

    // *
    // a-b
    // a
    private static Range parseRange(String value, CronFieldType type) {
        if (value.equals(ASTERISK)) {
            return type.getRange();
        } else {
            int hyphenPos = value.indexOf(HYPHEN);
            if (hyphenPos == -1) {
                // there is just 1 integer we need to parse.
                // If there is a parsing error, we will directly throw from here.
                int res = Integer.parseInt(value);
                // once we have parsed the int, we need to validate it against the type.
                boolean status = type.validateValue(res);
                if (status) {
                    return new Range(res, res);
                } else {
                    throw new IllegalArgumentException("Type validation failed for : " + type.getName());
                }
            } else {
                // we have a-b range that we can handle
                throw new IllegalArgumentException("Not implemented : " + value);
            }
        }
    }
}

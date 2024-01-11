package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.model.CronFieldType;
import org.deliveroo.cronparser.model.Range;

import java.util.List;

public class FieldParser {
    // This will serve as a common place to parse any cron field.
    public static CronField parseField(String value, CronFieldType type) {
        // VALIDATION
        if (value.isEmpty() || value.isBlank()) throw new IllegalArgumentException(type + " part should not be empty");

        CronField f = new CronField(value, type);

        if (type == CronFieldType.MINUTE)
            f.setRange(List.of(0, 15, 30, 45));

        if (type == CronFieldType.HOUR)
            f.setRange(List.of(0));

        if (type == CronFieldType.DAY_OF_MONTH)
            f.setRange(List.of(1, 15));

        if (type == CronFieldType.MONTH)
            f.setRange(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));

        if (type == CronFieldType.DAY_OF_WEEK)
            f.setRange(List.of(1, 2, 3, 4, 5));


        return f;
    }
}

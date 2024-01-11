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

        if(type == CronFieldType.HOUR)
            f.setRange(List.of(0));

        return f;
    }
}

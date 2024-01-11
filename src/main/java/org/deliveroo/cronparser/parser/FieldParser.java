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
            // HANDLE SLASH WHICH GIVES STEP INFORMATION.
            // slashes can be combined with ranges to specify step values
            int slashPos = field.indexOf(SLASH);

            if (slashPos == -1) {
                // no slash present => no steps, take the full range if present.
                // Only range left. Parse it using the type information and validate the ranges.
                f.setRange(parseRange(field, type));
            } else {
                // Anything with a slash is a range
                // 1/15 = [1-end]/15
                // */15 = [1-end]/15
                // 1-20/15 = [1-20]/15
                String rangeStr = field.substring(0, slashPos);
                String step = field.substring(slashPos + 1);
                Range range = parseRange(rangeStr, type);

                int delta = Integer.parseInt(step);
                if (delta <= 0) {
                    throw new IllegalArgumentException("Step is less than 0");
                }
                if (delta >= type.getRange().getMax()) {
                    throw new IllegalArgumentException("Step is greater than type range");
                }

                // here the delta is valid and range is valid.
                // 1/15 case.
                int hyphenPos = rangeStr.indexOf(HYPHEN);
                if (hyphenPos == -1) {
                    // 1 value, along with step => You need to start from start and keep going till end.
                    range = new Range(range.getMin(), type.getRange().getMax());
                }
                f.setRange(range, delta);
            }
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
                // we have a-b range that we can handle.
                String leftHalf = value.substring(0, hyphenPos);
                String rightHalf = value.substring(hyphenPos + 1);
                int left = Integer.parseInt(leftHalf);
                int right = Integer.parseInt(rightHalf);
                boolean leftStatus = type.validateValue(left);
                boolean rightStatus = type.validateValue(right);
                if (leftStatus && rightStatus) {
                    return new Range(left, right);
                } else {
                    throw new IllegalArgumentException("Type validation failed for : " + type.getName());
                }
            }
        }
    }
}

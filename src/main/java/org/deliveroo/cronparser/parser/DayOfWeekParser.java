package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.model.CronFieldType;

public class DayOfWeekParser implements Parser {

    @Override
    public CronField parse(String expression) {

        return FieldParser.parseField(expression, CronFieldType.DAY_OF_WEEK);
    }
}

package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.model.CronFieldType;

public class HourParser implements Parser {

    @Override
    public CronField parse(String expression) {
        // mock
        CronField f = new CronField(expression, CronFieldType.HOUR);

        return f;
    }
}

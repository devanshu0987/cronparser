package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.model.CronFieldType;

public class MinuteParser implements Parser {

    @Override
    public CronField parse(String expression) {
        // mock
        return FieldParser.parseField(expression, CronFieldType.MINUTE);
    }
}

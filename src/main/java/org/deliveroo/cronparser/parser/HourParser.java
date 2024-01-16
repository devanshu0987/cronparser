package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.model.CronFieldType;

import static org.deliveroo.cronparser.util.Constant.QUESTION_MARK;

public class HourParser implements Parser {

    @Override
    public CronField parse(String expression) {
        if (expression.contains(QUESTION_MARK)) {
            throw new IllegalArgumentException("Question Mark (?) not allowed with hour field");
        }

        return FieldParser.parseField(expression, CronFieldType.HOUR);
    }

    @Override
    public boolean isValid(String expression) {
        return false;
    }
}

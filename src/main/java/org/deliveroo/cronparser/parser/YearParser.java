package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.model.CronFieldType;

import static org.deliveroo.cronparser.util.Constant.QUESTION_MARK;

public class YearParser implements Parser{
    @Override
    public CronField parse(String expression) {
        if (expression.contains(QUESTION_MARK)) {
            throw new IllegalArgumentException("Question Mark (?) not allowed with year field");
        }
        return FieldParser.parseField(expression, CronFieldType.YEAR);
    }

    @Override
    public boolean isValid(String expression) {
        if(expression == null)
            return false;
        try {
            parse(expression);
            return true;
        } catch (IllegalArgumentException ex)
        {
            return false;
        }
    }
}

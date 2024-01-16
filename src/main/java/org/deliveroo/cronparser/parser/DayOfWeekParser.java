package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.model.CronFieldType;
import org.deliveroo.cronparser.util.Constant;

public class DayOfWeekParser implements Parser {

    String replaceWordsWithNumbers(String expression) {
        for (int index = 1; index < Constant.dayOfWeekList.size(); index++) {
            expression = expression.replaceAll(Constant.dayOfWeekList.get(index), String.valueOf(index));
        }
        return expression;
    }

    @Override
    public CronField parse(String expression) {
        expression = replaceWordsWithNumbers(expression);
        return FieldParser.parseField(expression, CronFieldType.DAY_OF_WEEK);
    }

    @Override
    public boolean isValid(String expression) {
        return false;
    }
}

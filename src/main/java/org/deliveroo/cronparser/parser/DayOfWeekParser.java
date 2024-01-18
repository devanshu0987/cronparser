package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.model.CronFieldType;
import org.deliveroo.cronparser.util.Constant;
import org.deliveroo.cronparser.util.Helper;

public class DayOfWeekParser implements Parser {

    @Override
    public CronField parse(String expression) {
        expression = Helper.replaceWordsWithNumbers(Constant.dayOfWeekList, expression);
        return FieldParser.parseField(expression, CronFieldType.DAY_OF_WEEK);
    }

    @Override
    public boolean isValid(String expression) {
        return false;
    }
}

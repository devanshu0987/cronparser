package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.model.CronFieldType;
import org.deliveroo.cronparser.util.Constant;
import org.deliveroo.cronparser.util.Helper;

import java.util.List;

import static org.deliveroo.cronparser.util.Constant.QUESTION_MARK;

public class MonthParser implements Parser {

    @Override
    public CronField parse(String expression) {
        if (expression.contains(QUESTION_MARK)) {
            throw new IllegalArgumentException("Question Mark (?) not allowed with month field");
        }
        expression = Helper.replaceWordsWithNumbers(Constant.monthList, expression);

        return FieldParser.parseField(expression, CronFieldType.MONTH);
    }

    @Override
    public boolean isValid(String expression) {
        return false;
    }
}

package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.model.CronFieldType;
import org.deliveroo.cronparser.util.Constant;

import java.util.List;

import static org.deliveroo.cronparser.util.Constant.QUESTION_MARK;

public class MonthParser implements Parser {

    String replaceWordsWithNumbers(String expression) {
        for (int index = 1; index < Constant.monthList.size(); index++) {
            expression = expression.replaceAll(Constant.monthList.get(index), String.valueOf(index));
            // case-insensitive replace implementation: make the expression to be lower case from the start.
        }
        return expression;
    }

    @Override
    public CronField parse(String expression) {
        if (expression.contains(QUESTION_MARK)) {
            throw new IllegalArgumentException("Question Mark (?) not allowed with month field");
        }
        expression = replaceWordsWithNumbers(expression);

        return FieldParser.parseField(expression, CronFieldType.MONTH);
    }

    @Override
    public boolean isValid(String expression) {
        return false;
    }
}

package org.deliveroo.cronparser.parser;


import org.deliveroo.cronparser.model.CronFieldType;
import org.deliveroo.cronparser.util.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.deliveroo.cronparser.util.Constant.SPACE;

public class CronExpressionTokenizer {
    private Map<CronFieldType, String> tokens;

    private List<String> validateExpression(String expression) {
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }

        List<String> tokens = List.of(expression.split(SPACE));

        if (tokens.size() != Constant.noOfParts) {
            throw new IllegalArgumentException("Invalid count of parts");
        }
        return tokens;
    }

    public CronExpressionTokenizer(String expression) {
        List<String> splitTokens = validateExpression(expression);
        tokens = new HashMap<>();

        tokens.put(CronFieldType.MINUTE, splitTokens.get(0));
        tokens.put(CronFieldType.HOUR, splitTokens.get(1));
        tokens.put(CronFieldType.DAY_OF_MONTH, splitTokens.get(2));
        tokens.put(CronFieldType.MONTH, splitTokens.get(3));
        tokens.put(CronFieldType.DAY_OF_WEEK, splitTokens.get(4));
        tokens.put(CronFieldType.COMMAND, splitTokens.get(5));
    }

    public String getToken(CronFieldType type) {
        return tokens.get(type);
    }
}

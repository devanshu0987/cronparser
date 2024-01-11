package org.deliveroo.cronparser.parser;


import org.deliveroo.cronparser.model.CronFieldType;
import org.deliveroo.cronparser.util.Constant;

import java.util.HashMap;
import java.util.Map;

import static org.deliveroo.cronparser.util.Constant.SPACE;

public class CronExpressionTokenizer {
    private Map<CronFieldType, String> tokens;

    private String[] validateExpression(String expression) {
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }

        String[] tokens = expression.split(SPACE);

        if (tokens.length != Constant.noOfParts) {
            throw new IllegalArgumentException("Invalid count of parts");
        }
        return tokens;
    }

    public CronExpressionTokenizer(String expression) {
        String[] splitTokens = validateExpression(expression);
        tokens = new HashMap<>();

        tokens.put(CronFieldType.MINUTE, splitTokens[0]);
        tokens.put(CronFieldType.HOUR, splitTokens[1]);
        tokens.put(CronFieldType.DAY_OF_MONTH, splitTokens[2]);
        tokens.put(CronFieldType.MONTH, splitTokens[3]);
        tokens.put(CronFieldType.DAY_OF_WEEK, splitTokens[4]);
        tokens.put(CronFieldType.COMMAND, splitTokens[5]);
    }

    public String getToken(CronFieldType type) {
        return tokens.get(type);
    }
}

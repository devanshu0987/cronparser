package org.deliveroo.cronparser.parser;


import org.deliveroo.cronparser.model.CronFieldType;
import org.deliveroo.cronparser.util.Constant;

import java.time.Year;
import java.util.*;

import static org.deliveroo.cronparser.util.Constant.SPACE;

public class CronExpressionTokenizer {
    private Map<CronFieldType, String> tokens;

    private List<String> validateExpression(String expression) {
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }

        List<String> tokens = new ArrayList<>(Arrays.asList(expression.split(SPACE)));

        if (tokens.size() < 6)
            throw new IllegalArgumentException("Invalid count of parts");

        // 0,1,2,3,4,5[YEAR OR COMMAND],Rest
        // Is there a way to find where command starts and where year ends
        // How do I know if 5th one is year or command.
        boolean isFifthPartYear = true;
        try {
            YearParser yearParser = new YearParser();
            yearParser.parse(tokens.get(5));
        } catch (Exception ex) {
            isFifthPartYear = false;
        }

        if (!isFifthPartYear)
            tokens.add(5, "*");

        // parse multipart command properly.
        List<String> multiPartCommand = tokens.subList(6, tokens.size());
        tokens.set(6, String.join(" ", multiPartCommand));

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
        tokens.put(CronFieldType.YEAR, splitTokens.get(5));
        tokens.put(CronFieldType.COMMAND, splitTokens.get(6));
    }

    public String getToken(CronFieldType type) {
        return tokens.get(type);
    }
}

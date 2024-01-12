package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronFieldType;

import java.util.HashMap;
import java.util.Map;

public class CronExpressionParser {
    private Map<CronFieldType, Parser> parserMap;

    public CronExpressionParser() {
        parserMap = new HashMap<>();
        parserMap.put(CronFieldType.MINUTE, new MinuteParser());
        parserMap.put(CronFieldType.HOUR, new HourParser());
        parserMap.put(CronFieldType.DAY_OF_MONTH, new DayOfMonthParser());
        parserMap.put(CronFieldType.MONTH, new MonthParser());
        parserMap.put(CronFieldType.DAY_OF_WEEK, new DayOfWeekParser());
    }

    public Parser getParserInstance(CronFieldType type) {
        return parserMap.get(type);
    }
}

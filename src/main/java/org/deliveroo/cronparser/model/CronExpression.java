package org.deliveroo.cronparser.model;

import org.deliveroo.cronparser.parser.*;

public class CronExpression {
    private CronField[] fields;
    private String expression;
    private String command;

    private CronExpression(CronField minutes, CronField hours, CronField daysOfMonth, CronField months,
                           CronField daysOfWeek, CronField year, String command, String expression) {

        this.fields = new CronField[]{minutes, hours, daysOfMonth, months, daysOfWeek, year};
        this.command = command;
        this.expression = expression;
    }

    public static CronExpression parse(String Expression) {

        CronExpressionTokenizer tokenizer = new CronExpressionTokenizer(Expression);
        CronExpressionParser parser = new CronExpressionParser();

        try {
            CronField minute = parser.getParserInstance(CronFieldType.MINUTE).parse(tokenizer.getToken(CronFieldType.MINUTE));
            CronField hour = parser.getParserInstance(CronFieldType.HOUR).parse(tokenizer.getToken(CronFieldType.HOUR));
            CronField dayOfMonth = parser.getParserInstance(CronFieldType.DAY_OF_MONTH).parse(tokenizer.getToken(CronFieldType.DAY_OF_MONTH));
            CronField month = parser.getParserInstance(CronFieldType.MONTH).parse(tokenizer.getToken(CronFieldType.MONTH));
            CronField dayOfWeek = parser.getParserInstance(CronFieldType.DAY_OF_WEEK).parse(tokenizer.getToken(CronFieldType.DAY_OF_WEEK));
            CronField year = parser.getParserInstance(CronFieldType.YEAR).parse(tokenizer.getToken(CronFieldType.YEAR));
            String command = tokenizer.getToken(CronFieldType.COMMAND);

            return new CronExpression(minute, hour, dayOfMonth, month, dayOfWeek, year, command, Expression);

        } catch (IllegalArgumentException ex) {
            // figure out a way to properly display messages here.
            throw new IllegalArgumentException(ex);
        }
    }

    public String printParsedExpression() {
        StringBuilder res = new StringBuilder();
        for (var f : fields) {
            res.append(f.printParsedExpression());
            res.append(System.lineSeparator());
        }

        res.append(String.format("%-14s", "COMMAND")).append(" : ").append(command);
        return res.toString();

    }

    public CronField[] getFields() {
        return fields;
    }

    public String getExpression() {
        return expression;
    }

    public String getCommand() {
        return command;
    }
}

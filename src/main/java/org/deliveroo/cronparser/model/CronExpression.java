package org.deliveroo.cronparser.model;

import org.deliveroo.cronparser.parser.*;

import static org.deliveroo.cronparser.util.Constant.SPACE;

public class CronExpression {
    private CronField[] fields;
    private String expression;
    private String command;

    public CronExpression() {
    }

    private CronExpression(CronField minutes,
                           CronField hours,
                           CronField daysOfMonth,
                           CronField months,
                           CronField daysOfWeek,
                           String command,
                           String expression) {

        this.fields = new CronField[]{minutes, hours, daysOfMonth, months, daysOfWeek};
        this.command = command;
        this.expression = expression;
    }

    // 5 Fields + 1 more field
    public static CronExpression Parse(String Expression) {

        String[] tokens = Expression.split(SPACE);

        try {
            CronField minute = (new MinuteParser()).parse(tokens[0]);
            CronField hour = (new HourParser()).parse(tokens[0]);
            CronField dayOfMonth = (new DayOfMonthParser()).parse(tokens[0]);
            CronField month = (new MonthParser()).parse(tokens[0]);
            CronField dayOfWeek = (new DayOfWeekParser()).parse(tokens[0]);
            String command = tokens[5];

            return new CronExpression(minute, hour, dayOfMonth, month, dayOfWeek, command, Expression);

        } catch (IllegalArgumentException ex) {
            // figure out a way to properly display messages here.
            throw new IllegalArgumentException(ex);
        }
    }

    public String printParsedExpression() {
        StringBuilder res = new StringBuilder();
        for (var f : fields) {
            res.append(f.toString());
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
}

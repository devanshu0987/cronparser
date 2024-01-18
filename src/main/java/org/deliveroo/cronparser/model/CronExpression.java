package org.deliveroo.cronparser.model;

import org.deliveroo.cronparser.parser.CronExpressionParser;
import org.deliveroo.cronparser.parser.CronExpressionTokenizer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    Temporal getNext(Temporal currentTime) {
        // we will do a brute force here.
        return LocalDateTime.now();

    }

    public String printParsedExpression(PrintConfiguration config) {
        StringBuilder res = new StringBuilder();
        for (var f : fields) {
            res.append(f.printParsedExpression(config));
            res.append(System.lineSeparator());
        }

        res.append(String.format("%-14s", "COMMAND")).append(" : ").append(command);
        return res.toString();

    }

    String printList(List<Integer> items) {
        int size = items.size();
        if (size == 1)
            return items.get(0).toString();
        else {
            var allButOne = items.subList(0, size - 1);
            String firstPart = String.join(",",
                    allButOne.stream().map((x) -> {
                        return String.valueOf(x);
                    }).collect(Collectors.toList()));
            return firstPart + " and " + items.get(size - 1).toString();
        }
    }

    boolean isFullRange(CronFieldType type, List<Integer> items) {
        int rangeSize = type.getRange().getMax() - type.getRange().getMin() + 1;
        if (items.size() == rangeSize)
            return true;
        return false;
    }

    public String printDescription() {
        String ans = "At ";
        for (var field : fields) {
            if (isFullRange(field.getType(), field.getItems())) {
                ans += "every " + field.getType().getName().toLowerCase();
            } else {
                ans += field.getType().getName().toLowerCase() + " " + printList(field.getItems());
            }

            if (field.getType() == CronFieldType.MINUTE)
                ans += " past ";
            else
                ans += " and ";
        }
        return ans;
    }

    public List<LocalDateTime> nextNInstances(LocalDateTime time, int n) {
        List<LocalDateTime> instances = new ArrayList<>();
        while (n-- > 0) {
            time = next(time);
            if (time.isEqual(LocalDateTime.MAX))
                break;
            instances.add(time);
            time = time.plus(1L, ChronoUnit.MINUTES);
        }
        return instances;
    }

    public List<LocalDateTime> prevNInstances(LocalDateTime time, int n) {
        List<LocalDateTime> instances = new ArrayList<>();
        while (n-- > 0) {
            time = prev(time);
            if (time.isEqual(LocalDateTime.MAX))
                break;
            instances.add(time);
            time = time.plus(-1L, ChronoUnit.MINUTES);
        }
        return instances;
    }

    public LocalDateTime next(LocalDateTime time) {
        LocalDateTime initialTime = time;
        time = time.truncatedTo(ChronoUnit.MINUTES);
        // Only explore next 50 years.
        while (Math.abs(time.getYear() - initialTime.getYear()) < 50) {
            int minute = time.get(ChronoField.MINUTE_OF_HOUR);
            int hour = time.get(ChronoField.HOUR_OF_DAY);
            int dayOfMonth = time.get(ChronoField.DAY_OF_MONTH);
            int month = time.get(ChronoField.MONTH_OF_YEAR);
            int dayOfWeek = time.get(ChronoField.DAY_OF_WEEK);
            int year = time.get(ChronoField.YEAR);

            if (!fields[5].getItems().contains(year)) {
                time = time.truncatedTo(ChronoUnit.DAYS);
                time = time.plus(1L, ChronoUnit.YEARS);
                // set the time to first day of year.
                time = time.withDayOfYear(1);
                continue;
            }
            if (!fields[3].getItems().contains(month)) {
                time = time.truncatedTo(ChronoUnit.DAYS);
                time = time.plus(1L, ChronoUnit.MONTHS);
                // set the time to first day of month.
                time = time.withDayOfMonth(1);

                continue;
            }
            if (!fields[4].getItems().contains(dayOfWeek) && !fields[2].getItems().contains(dayOfMonth)) {
                time = time.plus(1L, ChronoUnit.DAYS);
                time = time.truncatedTo(ChronoUnit.DAYS);
                continue;
            }

            if (!fields[1].getItems().contains(hour)) {
                time = time.plus(1L, ChronoUnit.HOURS);
                time = time.truncatedTo(ChronoUnit.HOURS);
                continue;
            }

            if (!fields[0].getItems().contains(minute)) {
                time = time.plus(1L, ChronoUnit.MINUTES);
                continue;
            }
            // reset seconds and milliseconds.
            time = time.truncatedTo(ChronoUnit.MINUTES);
            return time;
        }
        return LocalDateTime.MAX;
    }

    public LocalDateTime prev(LocalDateTime time) {
        LocalDateTime initialTime = time;
        time = time.truncatedTo(ChronoUnit.MINUTES);
        // Only explore next 50 years.
        while (Math.abs(time.getYear() - initialTime.getYear()) < 50) {
            int minute = time.get(ChronoField.MINUTE_OF_HOUR);
            int hour = time.get(ChronoField.HOUR_OF_DAY);
            int dayOfMonth = time.get(ChronoField.DAY_OF_MONTH);
            int month = time.get(ChronoField.MONTH_OF_YEAR);
            int dayOfWeek = time.get(ChronoField.DAY_OF_WEEK);
            int year = time.get(ChronoField.YEAR);

            if (!fields[5].getItems().contains(year)) {
                time = time.truncatedTo(ChronoUnit.DAYS);
                // time = time.withDayOfYear(1);
                time = time.plus(-1L, ChronoUnit.YEARS);
                // you should only go till last day of the year, you are skipping.
                continue;
            }
            if (!fields[3].getItems().contains(month)) {
                // time should be: 23:59.
                // the day should be the last day of the month.
                time = time.withHour(23).withMinute(59);
                var newTime = time;
                while (newTime.getMonth() == time.getMonth())
                    newTime = newTime.plus(-1L, ChronoUnit.DAYS);
                time = newTime;
                continue;
            }
            if (!fields[4].getItems().contains(dayOfWeek) && !fields[2].getItems().contains(dayOfMonth)) {
                // time = time.truncatedTo(ChronoUnit.DAYS);
                time = time.plus(-1L, ChronoUnit.DAYS);
                continue;
            }

            if (!fields[1].getItems().contains(hour)) {
                // time = time.truncatedTo(ChronoUnit.HOURS);
                time = time.plus(-1L, ChronoUnit.HOURS);
                continue;
            }

            if (!fields[0].getItems().contains(minute)) {
                time = time.plus(-1L, ChronoUnit.MINUTES);
                continue;
            }
            // reset seconds and milliseconds.
            time = time.truncatedTo(ChronoUnit.MINUTES);
            return time;
        }
        return LocalDateTime.MAX;
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

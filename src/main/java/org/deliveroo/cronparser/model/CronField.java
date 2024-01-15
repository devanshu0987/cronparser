package org.deliveroo.cronparser.model;

import org.deliveroo.cronparser.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class CronField {
    private List<Integer> items;
    private CronFieldType type;
    private String fieldString;

    public CronField(String unparsedFieldString, CronFieldType type) {
        items = new ArrayList<>();
        fieldString = unparsedFieldString;
        this.type = type;
    }

    public String printParsedExpression(PrintConfiguration config) {
        if (type == CronFieldType.MONTH && config.isMonthInWords()) {
            List<String> months = new ArrayList<>();
            for (var item : items) {
                months.add(Constant.monthList.get(item));
            }
            return String.format("%-14s", type.print()) + " : " + months;
        } else if (type == CronFieldType.DAY_OF_WEEK && config.isDayOfWeekInWords()) {
            List<String> dayOfWeek = new ArrayList<>();
            for (var item : items) {
                dayOfWeek.add(Constant.dayOfWeekList.get(item));
            }
            return String.format("%-14s", type.print()) + " : " + dayOfWeek;
        }
        return String.format("%-14s", type.print()) + " : " + items;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setRange(List<Integer> range) {
        items.addAll(range);
    }

    public void setRange(Range r) {
        for (int start = r.getMin(); start <= r.getMax(); start = start + 1) {
            items.add(start);
        }
    }

    public void setRange(Range r, int delta) {
        for (int start = r.getMin(); start <= r.getMax(); start = start + delta) {
            items.add(start);
        }
    }
}

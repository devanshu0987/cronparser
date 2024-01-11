package org.deliveroo.cronparser.model;

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

    @Override
    public String toString() {
        return String.format("%-14s", type) + " : " + items;
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

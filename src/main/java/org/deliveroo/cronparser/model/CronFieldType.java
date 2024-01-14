package org.deliveroo.cronparser.model;

public enum CronFieldType {

    MINUTE("MINUTE", new Range(0, 59)),
    HOUR("HOUR", new Range(0, 23)),
    DAY_OF_MONTH("DAY OF MONTH", new Range(1, 31)),
    MONTH("MONTH", new Range(1, 12)),
    DAY_OF_WEEK("DAY OF WEEK", new Range(1, 7)),
    YEAR("YEAR", new Range(1970, 2999)),
    COMMAND("COMMAND", new Range(-1, -1));

    private final String name;
    private final Range range;

    private CronFieldType(String name, Range range) {
        this.name = name;
        this.range = range;
    }
    public String print() {
        return name;
    }

    public Range getRange() {
        return range;
    }

    public boolean validateValue(int value) {
        return value >= this.getRange().getMin() && value <= this.getRange().getMax();
    }

    public String getName() {
        return name;
    }
}

package org.deliveroo.cronparser.model;

public class Range {
    /**
     * The minimum value.
     */
    private final int min;
    /**
     * The maximum value.
     */
    private final int max;

    public Range(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}

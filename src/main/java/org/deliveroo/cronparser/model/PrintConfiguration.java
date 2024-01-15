package org.deliveroo.cronparser.model;

public class PrintConfiguration {


    public boolean isDayOfWeekInWords() {
        return isDayOfWeekInWords;
    }

    public boolean isMonthInWords() {
        return isMonthInWords;
    }

    private boolean isDayOfWeekInWords;
    private boolean isMonthInWords;

    public PrintConfiguration() {
        isDayOfWeekInWords = false;
        isMonthInWords = false;
    }

    public PrintConfiguration(boolean isDayOfWeekInWords, boolean isMonthInWords) {
        this.isDayOfWeekInWords = isDayOfWeekInWords;
        this.isMonthInWords = isMonthInWords;
    }
}

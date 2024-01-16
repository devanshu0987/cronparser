package org.deliveroo.cronparser;

import org.deliveroo.cronparser.model.CronExpression;
import org.deliveroo.cronparser.model.PrintConfiguration;

public class Main {
    public static void main(String[] args) {
        CronExpression exp = CronExpression.parse("*/15 0 1,15 1-5 1-5 1970 ls -r -r");
        PrintConfiguration config = new PrintConfiguration(false, false);
        System.out.println(exp.printParsedExpression(config));
    }
}
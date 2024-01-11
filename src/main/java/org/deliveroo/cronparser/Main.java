package org.deliveroo.cronparser;

import org.deliveroo.cronparser.model.CronExpression;

public class Main {
    public static void main(String[] args) {

        CronExpression exp = CronExpression.Parse("*/15 0 1,15 * 1-5 ls");
        System.out.println(exp.printParsedExpression());
    }
}
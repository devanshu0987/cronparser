package org.deliveroo.cronparser;

import org.deliveroo.cronparser.model.CronExpression;

public class Main {
    public static void main(String[] args) {

        CronExpression exp = CronExpression.Parse(args[0]);
        System.out.println(exp.printParsedExpression());
    }
}
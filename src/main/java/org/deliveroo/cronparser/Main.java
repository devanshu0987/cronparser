package org.deliveroo.cronparser;

import org.deliveroo.cronparser.model.CronExpression;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Invalid Input");
        } else {
            CronExpression exp = CronExpression.parse(args[0]);
            System.out.println(exp.printParsedExpression());
        }
    }
}
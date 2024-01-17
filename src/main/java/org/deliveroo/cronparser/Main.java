package org.deliveroo.cronparser;

import org.deliveroo.cronparser.model.CronExpression;
import org.deliveroo.cronparser.model.PrintConfiguration;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        CronExpression exp = CronExpression.parse("*/15 0 1,15 5 1 1970 ls");
        PrintConfiguration config = new PrintConfiguration(false, false);
        System.out.println(exp.printParsedExpression(config));

        // System.out.println(exp.printDescription());

        LocalDateTime now = LocalDateTime.now();

//        LocalDateTime nextInstance = exp.next(now);
//        System.out.println(nextInstance);
        var nextOneInstance = exp.nextNInstances(now, 20);
        System.out.println("N instances");
        for (var item : nextOneInstance)
            System.out.println(item);
    }
}
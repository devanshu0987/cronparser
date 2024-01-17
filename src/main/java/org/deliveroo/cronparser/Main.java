package org.deliveroo.cronparser;

import org.deliveroo.cronparser.model.CronExpression;
import org.deliveroo.cronparser.model.PrintConfiguration;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        CronExpression exp = CronExpression.parse("*/15 0 1,15 5 1 * ls");
        PrintConfiguration config = new PrintConfiguration(false, false);


        // System.out.println(exp.printParsedExpression(config));




    }
}
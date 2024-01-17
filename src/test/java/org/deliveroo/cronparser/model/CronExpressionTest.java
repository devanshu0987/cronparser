package org.deliveroo.cronparser.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CronExpressionTest {

    @Test
    public void testHappyPath() {
        CronExpression exp = CronExpression.parse("*/15 0 1,15 * 1-5 ls");
        assertEquals(exp.getFields()[0].getItems(), List.of(0, 15, 30, 45));
        assertEquals(exp.getFields()[1].getItems(), List.of(0));
        assertEquals(exp.getFields()[2].getItems(), List.of(1, 15));
        assertEquals(exp.getFields()[3].getItems(), List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        assertEquals(exp.getFields()[4].getItems(), List.of(1, 2, 3, 4, 5));
    }

    @Test
    public void testHappyPathStar() {
        CronExpression exp = CronExpression.parse("5,10 0 2 * * ls");
        assertEquals(exp.getFields()[0].getItems(), List.of(5, 10));
        assertEquals(exp.getFields()[1].getItems(), List.of(0));
        assertEquals(exp.getFields()[2].getItems(), List.of(2));
        assertEquals(exp.getFields()[3].getItems(), List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        assertEquals(exp.getFields()[4].getItems(), List.of(1, 2, 3, 4, 5, 6, 7));
    }

    @Test
    public void testYear() {
        CronExpression exp = CronExpression.parse("5,10 0 2 * * 1970 ls");
        assertEquals(exp.getFields()[0].getItems(), List.of(5, 10));
        assertEquals(exp.getFields()[1].getItems(), List.of(0));
        assertEquals(exp.getFields()[2].getItems(), List.of(2));
        assertEquals(exp.getFields()[3].getItems(), List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        assertEquals(exp.getFields()[4].getItems(), List.of(1, 2, 3, 4, 5, 6, 7));
    }

    @Test
    public void testMultiPartCommand() {
        CronExpression exp = CronExpression.parse("5,10 0 2 * * 1970 ls -r");
        assertEquals(exp.getFields()[0].getItems(), List.of(5, 10));
        assertEquals(exp.getFields()[1].getItems(), List.of(0));
        assertEquals(exp.getFields()[2].getItems(), List.of(2));
        assertEquals(exp.getFields()[3].getItems(), List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        assertEquals(exp.getFields()[4].getItems(), List.of(1, 2, 3, 4, 5, 6, 7));
        assertEquals(exp.getFields()[5].getItems(), List.of(1970));
        assertEquals("ls -r", exp.getCommand());

        CronExpression exp2 = CronExpression.parse("5,10 0 2 * * ls -r");
        assertEquals(exp2.getFields()[0].getItems(), List.of(5, 10));
        assertEquals(exp2.getFields()[1].getItems(), List.of(0));
        assertEquals(exp2.getFields()[2].getItems(), List.of(2));
        assertEquals(exp2.getFields()[3].getItems(), List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        assertEquals(exp2.getFields()[4].getItems(), List.of(1, 2, 3, 4, 5, 6, 7));
        int diff = CronFieldType.YEAR.getRange().getMax() - CronFieldType.YEAR.getRange().getMin() + 1;
        assertEquals(exp2.getFields()[5].getItems().size(), diff);
        assertEquals("ls -r", exp2.getCommand());
    }

    @Test
    public void testException() {
        assertThrows(IllegalArgumentException.class, () -> CronExpression.parse("1 0 2 1 15 ls"));
    }

    @Test
    public void testQuestionMark() {
        CronExpression exp = CronExpression.parse("5,10 0 2 * ? ls");
        assertEquals(exp.getFields()[0].getItems(), List.of(5, 10));
        assertEquals(exp.getFields()[1].getItems(), List.of(0));
        assertEquals(exp.getFields()[2].getItems(), List.of(2));
        assertEquals(exp.getFields()[3].getItems(), List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        assertEquals(exp.getFields()[4].getItems(), List.of(1, 2, 3, 4, 5, 6, 7));
    }

    @Test
    public void testNextAndPrev() {
        CronExpression exp = CronExpression.parse("*/15 0 1,15 5 1 * ls");
        LocalDateTime now = LocalDateTime.parse("2013-11-09T08:12");

        LocalDateTime startOfYear = now.withDayOfYear(1);
        LocalDateTime nextstartOfYear = startOfYear.plus(1L, ChronoUnit.YEARS);
        var nextN = exp.nextNInstances(startOfYear, 24);
        var prevN = exp.prevNInstances(nextstartOfYear, 24);
        Collections.reverse(prevN);

        for (int i = 0; i < nextN.size(); i++) {
            if (!nextN.get(i).equals(prevN.get(i))) {
                System.out.println("Diff : " + nextN.get(i) + " " + prevN.get(i));
            }
        }
    }

    @Test
    public void testRandom()
    {
        CronExpression exp = CronExpression.parse("*/15 0 1,15 5 1 * ls");
        Random rand = new Random();
        for(int i = 0; i < 20; i++)
        {
            int add = rand.nextInt() % 10000;
            LocalDateTime curr = LocalDateTime.parse("2013-11-09T08:12").plus(add, ChronoUnit.MINUTES);
            var prevOfNext = exp.prev(exp.next(curr));
            var nextOfPrev = exp.next(exp.prev(curr));
            assertEquals(prevOfNext, nextOfPrev, curr.toString());
        }

    }
}

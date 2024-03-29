package org.deliveroo.cronparser.model;

import org.junit.jupiter.api.Test;

import java.util.List;

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
    public void testException() {
        assertThrows(IllegalArgumentException.class, () -> CronExpression.parse("1 0 2 1 15 ls"));
    }

    @Test
    public void testQuestionMark(){
        CronExpression exp = CronExpression.parse("5,10 0 2 * ? ls");
        assertEquals(exp.getFields()[0].getItems(), List.of(5, 10));
        assertEquals(exp.getFields()[1].getItems(), List.of(0));
        assertEquals(exp.getFields()[2].getItems(), List.of(2));
        assertEquals(exp.getFields()[3].getItems(), List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        assertEquals(exp.getFields()[4].getItems(), List.of(1, 2, 3, 4, 5, 6, 7));
    }
}

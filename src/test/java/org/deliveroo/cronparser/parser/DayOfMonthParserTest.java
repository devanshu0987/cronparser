package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayOfMonthParserTest {
    @Test
    public void testOneValue() {
        CronField dayOfMonth = (new DayOfMonthParser()).parse("1");
        assertEquals(dayOfMonth.getItems(), List.of(1));
    }
}

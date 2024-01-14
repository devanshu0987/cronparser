package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayOfWeekParserTest {
    @Test
    public void testOneValue() {
        CronField dayOfWeek = (new DayOfWeekParser()).parse("1");
        assertEquals(dayOfWeek.getItems(), List.of(1));
    }

    @Test
    public void testOneValueWord() {
        CronField dayOfWeek = (new DayOfWeekParser()).parse("SAT");
        assertEquals(dayOfWeek.getItems(), List.of(7));
    }
}

package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonthParserTest {
    @Test
    public void testOneValue() {
        CronField month = (new MonthParser()).parse("1");
        assertEquals(month.getItems(), List.of(1));
    }
}

package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HourParserTest {
    @Test
    public void testOneValue() {
        CronField hour = (new HourParser()).parse("1");
        assertEquals(hour.getItems(), List.of(1));
    }
}

package org.deliveroo.cronparser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.parser.MinuteParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParseMinuteTests {

    @Test
    public void OneValue() {
        CronField minute = (new MinuteParser()).parse("1");
        assertEquals(minute.getItems(), List.of(1));
    }

    @Test
    public void Comma() {
        CronField minute = (new MinuteParser()).parse("1,5,6");
        assertEquals(minute.getItems(), List.of(1, 5, 6));
    }

}

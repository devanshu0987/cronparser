package org.deliveroo.cronparser.parser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.model.CronFieldType;
import org.deliveroo.cronparser.model.Range;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FieldParserTest {
    @Test
    public void testOneValue() {
        CronField hour = FieldParser.parseField("1", CronFieldType.HOUR);
        assertEquals(hour.getItems(), List.of(1));
    }

    @Test
    public void testValidation() {
        assertThrows(IllegalArgumentException.class, () -> FieldParser.parseField("", CronFieldType.HOUR));
        assertThrows(IllegalArgumentException.class, () -> FieldParser.parseField(" ", CronFieldType.HOUR));
        assertThrows(IllegalArgumentException.class, () -> FieldParser.parseField("1/0", CronFieldType.HOUR));
    }

    @Test
    public void testHyphenRange() {
        Range r = FieldParser.parseRange("2-3", CronFieldType.DAY_OF_MONTH);
        assertEquals(r.getMin(), 2);
        assertEquals(r.getMax(), 3);
    }

    @Test
    public void testStarRange() {
        Range r = FieldParser.parseRange("*", CronFieldType.DAY_OF_MONTH);
        assertEquals(r.getMin(), 1);
        assertEquals(r.getMax(), 31);
    }
}

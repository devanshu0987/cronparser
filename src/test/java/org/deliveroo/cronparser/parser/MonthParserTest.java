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

    @Test
    public void testOneWord() {
        // At which level should we handle this?
        // Tokenizer OR IN Parser
        // We are short-circuiting the flow here and going directly to MonthParser.
        // Ideally it should also work here and there.
        // Conversion isn't the job of Tokenizer.

        CronField month = (new MonthParser()).parse("JAN");
        assertEquals(month.getItems(), List.of(1));

        CronField month2 = (new MonthParser()).parse("JAN-FEB");
        assertEquals(month2.getItems(), List.of(1,2));

        CronField month3 = (new MonthParser()).parse("JAN-9");
        assertEquals(month3.getItems(), List.of(1,2,3,4,5,6,7,8,9));
    }
}

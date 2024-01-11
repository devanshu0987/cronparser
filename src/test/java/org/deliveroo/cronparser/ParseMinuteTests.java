package org.deliveroo.cronparser;

import org.deliveroo.cronparser.model.CronField;
import org.deliveroo.cronparser.parser.MinuteParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    public void Star() {
        CronField minute = (new MinuteParser()).parse("*");
        assertEquals(minute.getItems(), List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
                40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59));
    }

    @Test
    public void Hyphen() {
        CronField minute = (new MinuteParser()).parse("1-10");
        assertEquals(minute.getItems(), List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Test
    public void StarSlash() {
        CronField minute = (new MinuteParser()).parse("*/15");
        assertEquals(minute.getItems(), List.of(0, 15, 30, 45));
    }

    @Test
    public void HyphenSlash() {
        CronField minute = (new MinuteParser()).parse("1-48/15");
        assertEquals(minute.getItems(), List.of(1, 16, 31, 46));
    }

    // Only 1 value works like x-end of range before slash.
    @Test
    public void OneValueSlashOne() {
        CronField minute = (new MinuteParser()).parse("1/1");
        assertEquals(minute.getItems(), List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
                18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
                43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59));
    }

    @Test
    public void OneValueSlash() {
        CronField minute = (new MinuteParser()).parse("0/15");
        assertEquals(minute.getItems(), List.of(0, 15, 30, 45));
    }

    @Test
    public void OneValueSlash2() {
        CronField minute = (new MinuteParser()).parse("1/15");
        assertEquals(minute.getItems(), List.of(1, 16, 31, 46));
    }

    @Test
    public void CommaSlash() {
        CronField minute = (new MinuteParser()).parse("2,4,6/6");
        assertEquals(minute.getItems(), List.of(2, 4, 6, 12, 18, 24, 30, 36, 42, 48, 54));
    }

    @Test
    public void IllegalCharsParsingErrorTest() {
        List<String> testCases = List.of(
                "/15", " /15", "hello/world", "1/a",
                "a-b", "*-b", "hello-world", "a-b-c-d",
                "hello", "     "
        );
        for (var testCase : testCases) {
            assertThrows(IllegalArgumentException.class, () -> (new MinuteParser()).parse(testCase));
        }
    }

    @Test
    public void IllegalRangeParsingErrorTest() {
        List<String> testCases = List.of(
                "1000/5", "-1/5", "600/5", "400-299/5","1-500/5", // first part wrong
                "1/65", "1/-5", "1/0", "1/5-6", "1/*", "1/-", "1/ ", "1/,,,,", // step is wrong
                "1-200", "1,200",
                "1,,200",
                "?"
        );
        for (var testCase : testCases) {
            assertThrows(IllegalArgumentException.class, () -> (new MinuteParser()).parse(testCase));
        }
    }

}

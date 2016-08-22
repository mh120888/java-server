package com.github.mh120888.basichttpmessage;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasicHeaderParserTest {
    @Test
    public void splitUpRangeHeaderReturnsTheStartingAndEndingBytes() {
        BasicHeaderParser parser = new BasicHeaderParser();
        String[] result = parser.splitUpRangeHeader("bytes=500-600");

        assertEquals("500", result[0]);
        assertEquals("600", result[1]);
    }

    @Test
    public void splitUpRangeHeaderCanHandleAMissingStartingByte() {
        BasicHeaderParser parser = new BasicHeaderParser();
        String[] result = parser.splitUpRangeHeader("bytes=-4");

        assertEquals("", result[0]);
        assertEquals("4", result[1]);
    }

    @Test
    public void splitUpRangeHeaderCanHandleAMissingEndingByte() {
        BasicHeaderParser parser = new BasicHeaderParser();
        String[] result = parser.splitUpRangeHeader("bytes=4-");

        assertEquals("4", result[0]);
        assertEquals(1, result.length);
    }

    @Test
    public void calculateStartAndEndBytesReturnsIntArrayOfStartAndEndIfBothAreProvided() {
        BasicHeaderParser parser = new BasicHeaderParser();
        String[] input = {"1", "2"};
        byte[] mockResource = new byte[100];
        int[] result = parser.calculateStartAndEndBytes(input, mockResource);

        assertEquals(1, result[0]);
        assertEquals(2, result[1]);
    }

    @Test
    public void calculateStartAndEndBytesReturnsLastXBytesIfStartIsNotProvided() {
        BasicHeaderParser parser = new BasicHeaderParser();
        String[] input = {"", "2"};
        byte[] mockResource = new byte[100];
        int[] result = parser.calculateStartAndEndBytes(input, mockResource);

        assertEquals(98, result[0]);
        assertEquals(99, result[1]);
    }

    @Test
    public void calculateStartAndEndBytesReturnsBytesFromStartingPointUntilEndIfNoEndIsGiven() {
        BasicHeaderParser parser = new BasicHeaderParser();
        String[] input = {"4"};
        byte[] mockResource = new byte[100];
        int[] result = parser.calculateStartAndEndBytes(input, mockResource);

        assertEquals(4, result[0]);
        assertEquals(99, result[1]);
    }

    @Test
    public void isStartOfRangeMissingReturnsTrueIfTheStartOfTheRangeIsNotGiven() {
        BasicHeaderParser parser = new BasicHeaderParser();
        String[] range = {"", "2"};

        assertTrue(parser.isStartOfRangeMissing(range));
    }

    @Test
    public void isStartOfRangeMissingReturnsFalseIfTheStartOfTheRangeIsGiven() {
        BasicHeaderParser parser = new BasicHeaderParser();
        String[] range = {"1", "2"};

        assertFalse(parser.isStartOfRangeMissing(range));
    }

    @Test
    public void isEndOfRangeMissingReturnsTrueIfTheEndOfTheRangeIsNotGiven() {
        BasicHeaderParser parser = new BasicHeaderParser();
        String[] range = {"1"};

        assertTrue(parser.isEndOfRangeMissing(range));
    }

    @Test
    public void isEndOfRangeMissingReturnsFalseIfTheEndOfTheRangeIsGiven() {
        BasicHeaderParser parser = new BasicHeaderParser();
        String[] range = {"1", "2"};

        assertFalse(parser.isEndOfRangeMissing(range));
    }
}
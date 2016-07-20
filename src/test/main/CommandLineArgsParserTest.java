package main;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/15/16.
 */
public class CommandLineArgsParserTest {
    @Test
    public void groupOptionsMatchesSingleFlagWithValue() {
        String[] args = {"-f",  "somethingRandom"};
        HashMap<String, String> parsedArgs = CommandLineArgsParser.groupOptions(args);

        assertTrue(parsedArgs.containsKey("-f"));
        assertEquals("somethingRandom", parsedArgs.get("-f"));
    }

    @Test
    public void groupOptionsMatchesMultipleFlagsWithCorrectValues() {
        String[] args = {"-f",  "somethingRandom", "-ff", "evenMoreRandom"};
        HashMap<String, String> parsedArgs = CommandLineArgsParser.groupOptions(args);

        assertTrue(parsedArgs.containsKey("-ff"));
        assertEquals("evenMoreRandom", parsedArgs.get("-ff"));
    }

    @Test
    public void groupOptionsReturnsAnEmptyHashmapIfGivenAnEmptyArray() {
        String[] args = {};
        HashMap<String, String> parsedArgs = CommandLineArgsParser.groupOptions(args);

        assertEquals(0, parsedArgs.size());
    }

    @Test
    public void groupOptionsIgnoresFlagsWithoutALeadingDash() {
        String[] args = {"f",  "somethingRandom", "-ff", "evenMoreRandom"};
        HashMap<String, String> parsedArgs = CommandLineArgsParser.groupOptions(args);

        assertFalse(parsedArgs.containsKey("f"));
        assertTrue(parsedArgs.containsKey("-ff"));
    }

    @Test
    public void groupOptionsReturnsAnEmptyHashmapIfGivenAnArrayOfOddLength() {
        String[] args = {"-oneflag", "secondValue",  "-flagWithNoValue"};
        HashMap<String, String> parsedArgs = CommandLineArgsParser.groupOptions(args);

        assertEquals(0, parsedArgs.size());
    }
}
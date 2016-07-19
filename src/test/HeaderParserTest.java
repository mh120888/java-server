package test;

import org.junit.Test;
import server.HeaderParser;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class HeaderParserTest {
    @Test
    public void parseReturnsAHashmapWithCorrectHeader() {
        HashMap<String, String> result = HeaderParser.parse("Some header: some value");

        assertTrue(result.containsKey("Some header"));
    }

    @Test
    public void parseReturnsAHashmapWithCorrectHeaderAndValueWithoutExtraWhitespace() {
        HashMap<String, String> result = HeaderParser.parse("Some header: some value    ");

        assertEquals("some value", result.get("Some header"));
    }

    @Test
    public void parseReturnsAHashmapWithMultipleHeadersIfAppropriate() {
        HashMap<String, String> result = HeaderParser.parse("Some header: some value\nAnother header: foobar");

        assertEquals(2, result.size());
    }

    @Test
    public void parseReturnsCorrectResultWhenHeaderValueAlsoContainsColons() {
        HashMap<String, String> result = HeaderParser.parse("Some header: some value\nAnother header: foo:bar");

        assertEquals("foo:bar", result.get("Another header"));
    }
}
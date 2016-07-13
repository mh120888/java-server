import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

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
}
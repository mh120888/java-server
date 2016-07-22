package httprequest;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class HeaderParserTest {
    @Test
    public void parseRangeHeaderReturnsTheStartingAndEndingBytes() {
        HeaderParser parser = new HeaderParser();
        int[] result = parser.parseRangeHeader("bytes=500-600");

        assertEquals(result[0], 500);
        assertEquals(result[1], 600);
    }
}
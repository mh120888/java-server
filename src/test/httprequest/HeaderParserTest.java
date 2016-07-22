package httprequest;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class HeaderParserTest {
    @Test
    public void splitUpRangeHeaderReturnsTheStartingAndEndingBytes() {
        HeaderParser parser = new HeaderParser();
        String[] result = parser.splitUpRangeHeader("bytes=500-600");

        assertEquals("500", result[0]);
        assertEquals("600", result[1]);
    }

    @Test
    public void splitUpRangeHeaderCanHandleAMissingStartingByte() {
        HeaderParser parser = new HeaderParser();
        String[] result = parser.splitUpRangeHeader("bytes=-4");

        assertEquals("", result[0]);
        assertEquals("4", result[1]);
    }

    @Test
    public void splitUpRangeHeaderCanHandleAMissingEndingByte() {
        HeaderParser parser = new HeaderParser();
        String[] result = parser.splitUpRangeHeader("bytes=4-");

        assertEquals("4", result[0]);
        assertEquals(1, result.length);
    }

    @Test
    public void calculateStartAndEndBytesReturnsIntArrayOfStartAndEndIfBothAreProvided() {
        HeaderParser parser = new HeaderParser();
        String[] input = {"1", "2"};
        byte[] mockResource = new byte[100];
        int[] result = parser.calculateStartAndEndBytes(input, mockResource);

        assertEquals(1, result[0]);
        assertEquals(2, result[1]);
    }

    @Test
    public void calculateStartAndEndBytesReturnsLastXBytesIfStartIsNotProvided() {
        HeaderParser parser = new HeaderParser();
        String[] input = {"", "2"};
        byte[] mockResource = new byte[100];
        int[] result = parser.calculateStartAndEndBytes(input, mockResource);

        assertEquals(98, result[0]);
        assertEquals(100, result[1]);
    }

    @Test
    public void calculateStartAndEndBytesReturnsBytesFromStartingPointUntilEndIfNoEndIsGiven() {
        HeaderParser parser = new HeaderParser();
        String[] input = {"4"};
        byte[] mockResource = new byte[100];
        int[] result = parser.calculateStartAndEndBytes(input, mockResource);

        assertEquals(4, result[0]);
        assertEquals(100, result[1]);
    }
}
package request;

/**
 * Created by matthewhiggins on 7/22/16.
 */
public interface HeaderParser {
    int[] parseRangeHeader(String rangeHeaderValue, byte[] resourceContents);
}

package request;

/**
 * Created by matthewhiggins on 7/22/16.
 */
public abstract class AbstractHeaderParser {
    public abstract int[] parseRangeHeader(String rangeHeaderValue, byte[] resourceContents);
}

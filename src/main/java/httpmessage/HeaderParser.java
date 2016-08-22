package httpmessage;

public interface HeaderParser {
    int[] parseRangeHeader(String rangeHeaderValue, byte[] resourceContents);
}

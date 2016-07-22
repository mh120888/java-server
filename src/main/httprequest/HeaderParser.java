package httprequest;

import abstracthttprequest.AbstractHeaderParser;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class HeaderParser extends AbstractHeaderParser {
    public int[] parseRangeHeader(String rangeHeaderValue) {
        String[] startEndAsString = rangeHeaderValue.split("=")[1].split("-");
        int[] startEnd = new int[startEndAsString.length];
        for (int i = 0; i < startEndAsString.length; i++) {
            startEnd[i] = Integer.parseInt(startEndAsString[i].trim());
        }

        return startEnd;
    }
}

package httprequest;

import abstracthttprequest.AbstractHeaderParser;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class HeaderParser extends AbstractHeaderParser {
    public int[] parseRangeHeader(String rangeHeaderValue, byte[] resourceContents) {
        String[] startEnd = splitUpRangeHeader(rangeHeaderValue);
        return calculateStartAndEndBytes(startEnd, resourceContents);
    }

    String[] splitUpRangeHeader(String rangeHeaderValue) {
        String[] startEndAsString = rangeHeaderValue.split("=")[1].split("-");
        String[] startEnd = new String[startEndAsString.length];
        for (int i = 0; i < startEndAsString.length; i++) {
            startEnd[i] = startEndAsString[i].trim();
        }
        return startEndAsString;
    }

     int[] calculateStartAndEndBytes(String[] startAndEndStrings, byte[] resourceContents) {
        int[] result = new int[2];
        int lengthOfResource = resourceContents.length;
        if (startAndEndStrings[0].isEmpty()) {
            result[0] = resourceContents.length - Integer.parseInt(startAndEndStrings[1]);
            result[1] = resourceContents.length;
        } else if (startAndEndStrings.length == 1) {
            result[0] = Integer.parseInt(startAndEndStrings[0]);
            result[1] = resourceContents.length;
        } else {
            for (int i = 0; i < startAndEndStrings.length; i++) {
                result[i] = Integer.parseInt(startAndEndStrings[i]);
            }
        }
        return result;
    }
}

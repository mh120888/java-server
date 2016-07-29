package httprequest;

import request.HeaderParser;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class BasicHeaderParser implements HeaderParser {
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
        if (isStartOfRangeMissing(startAndEndStrings)) {
            result[0] = lengthOfResource - Integer.parseInt(startAndEndStrings[1]);
            result[1] = lengthOfResource - 1;
        } else if (isEndOfRangeMissing(startAndEndStrings)) {
            result[0] = Integer.parseInt(startAndEndStrings[0]);
            result[1] = lengthOfResource - 1;
        } else {
            for (int i = 0; i < startAndEndStrings.length; i++) {
                result[i] = Integer.parseInt(startAndEndStrings[i]);
            }
        }
        return result;
    }

    boolean isStartOfRangeMissing(String[] range) {
        return range[0].isEmpty();
    }

    boolean isEndOfRangeMissing(String[] range) {
        return range.length == 1;
    }
}

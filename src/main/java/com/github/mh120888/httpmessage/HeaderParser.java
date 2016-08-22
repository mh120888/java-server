package com.github.mh120888.httpmessage;

public interface HeaderParser {
    int[] parseRangeHeader(String rangeHeaderValue, byte[] resourceContents);
}

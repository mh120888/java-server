package com.github.mh120888.basichttpmessage;

import org.junit.Assert;
import org.junit.Test;

public class RequestParserTest {
    static RequestParser requestParser;

    @Test
    public void getMethodParsesTheCorrectMethod() {
        requestParser = new RequestParser("GET /somepath HTTP/1.1");
        String result = requestParser.getMethod();

        Assert.assertEquals("GET", result);
    }

    @Test
    public void getPathParsesTheCorrectPath() {
        requestParser = new RequestParser("GET /somepath HTTP/1.1");
        String result = requestParser.getPath();

        Assert.assertEquals("/somepath", result);
    }

    @Test
    public void getVersionParsesTheCorrectHTTPVersion() {
        requestParser = new RequestParser("GET /somepath HTTP/1.1");
        String result = requestParser.getVersion();

        Assert.assertEquals("HTTP/1.1", result);
    }
}
package server;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class HTTPResponseBuilderTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void buildResponseReturnsTheCorrectFormatForA404Response() {
        HashMap<String, String> request = new HashMap<>();
        request.put("responseLine", "HTTP/1.1 404 Not Found");
        String expectedResult = "HTTP/1.1 404 Not Found";

        Assert.assertEquals(expectedResult, HTTPResponseBuilder.buildResponse(request));
    }

    @Test
    public void buildResponseReturnsCorrectFormatForA200Response() {
        HashMap<String, String> request = new HashMap<>();
        request.put("responseLine", "HTTP/1.1 200 OK");
        request.put("body", "something in the body");
        String expectedResult = "HTTP/1.1 200 OK\n\nsomething in the body";

        Assert.assertEquals(expectedResult, HTTPResponseBuilder.buildResponse(request));
    }

    @Test
    public void buildResponseReturnsCorrectFormatForResponseWithHeaders() {
        HashMap<String, String> request = new HashMap<>();
        request.put("responseLine", "HTTP/1.1 200 OK");
        request.put("headers", "WWW-Authenticate: Basic realm=\"User Visible Realm\"");
        request.put("body", "something in the body");
        String expectedResult = "HTTP/1.1 200 OK\nWWW-Authenticate: Basic realm=\"User Visible Realm\"\n\nsomething in the body";

        Assert.assertEquals(expectedResult, HTTPResponseBuilder.buildResponse(request));
    }
}
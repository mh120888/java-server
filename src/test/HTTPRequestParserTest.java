package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.HTTPRequestParser;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by matthewhiggins on 7/10/16.
 */
public class HTTPRequestParserTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void parseReturnsAGetMethodForAGetRequest() {
        HashMap<String, String> result = HTTPRequestParser.parse("GET / HTTP/1.1");

        assertEquals("GET", result.get("method"));
    }

    @Test
    public void parseReturnsAPostMethodForAPostRequest() {
        HashMap<String, String> result = HTTPRequestParser.parse("POST / HTTP/1.1");

        assertEquals("POST", result.get("method"));
    }

    @Test
    public void parseReturnsCorrectPath() {
        HashMap<String, String> result = HTTPRequestParser.parse("GET / HTTP/1.1");

        assertEquals("/", result.get("path"));
    }

    @Test
    public void parseReturnsCorrectPathEvenWhenItIsNotValid() {
        HashMap<String, String> result = HTTPRequestParser.parse("GET /not-a-real-path HTTP/1.1");

        assertEquals("/not-a-real-path", result.get("path"));
    }

    @Test
    public void parseReturnsTheCorrectHTTPVersion() {
        HashMap<String, String> result = HTTPRequestParser.parse("GET /not-a-real-path HTTP/1.1");

        assertEquals("HTTP/1.1", result.get("httpVersion"));
    }

//    @Test
//    public void parseReturnsRequestBodyForPOSTRequestsWithParams() {
//        HashMap<String, String> result = server.HTTPRequestParser.parse("POST / HTTP/1.1\n\nusername=zurfyx&pass=password");
//
//        assertEquals("username=zurfyx&pass=password", result.get("body"));
//    }

    @Test
    public void parseReturnsAuthorizationHeaderIfGiven() {
        HashMap<String, String> result = HTTPRequestParser.parse("POST / HTTP/1.1\nAuthorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        assertEquals("Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==", result.get("headers"));
    }

    @Test
    public void parseReturnsNoBodyForGetRequests() {
        HashMap<String, String> result = HTTPRequestParser.parse("GET / HTTP/1.1");

        assertEquals(null, result.get("body"));
    }
}
package cobspecapp;

import abstracthttprequest.AbstractHttpRequest;
import httprequest.HTTPRequest;
import org.junit.Test;
import server.HeaderParser;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class OptionsResourceHandlerTest {
    @Test
    public void getReponseDataReturnsCorrectResponseLineForGET() {
        OptionsResourceHandler endpoint = new OptionsResourceHandler();
        AbstractHttpRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

    @Test
    public void getReponseDataReturnsCorrectResponseLineForOPTIONS() {
        OptionsResourceHandler endpoint = new OptionsResourceHandler();
        AbstractHttpRequest request = new HTTPRequest("OPTIONS /pathdoesnotmatter HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

    @Test
    public void getResponseDataReturnsAResponseWithAnAllowHeader() {
        OptionsResourceHandler endpoint = new OptionsResourceHandler();
        AbstractHttpRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);
        HashMap<String, String> headers = HeaderParser.parse(response.get("headers"));

        assertEquals(true, headers.containsKey("Allow"));
    }

    @Test
    public void getResponseDataReturnsAResponseWithAllowHeaderWithMethodsAllowedForGivenPath() {
        OptionsResourceHandler endpoint = new OptionsResourceHandler();
        AbstractHttpRequest request = new HTTPRequest("GET /method_options HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);
        HashMap<String, String> headers = HeaderParser.parse(response.get("headers"));
        String[] acceptedMethods = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};

        for (String method : acceptedMethods) {
            assertEquals(true, headers.get("Allow").contains(method));
        }
    }

    @Test
    public void getResponseDataReturnsAResponseWithAllowHeaderWithMethodsAllowedForADifferentPath() {
        OptionsResourceHandler endpoint = new OptionsResourceHandler();
        AbstractHttpRequest request = new HTTPRequest("GET /method_options2 HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);
        HashMap<String, String> headers = HeaderParser.parse(response.get("headers"));
        String[] acceptedMethods = {"GET", "OPTIONS"};

        for (String method : acceptedMethods) {
            assertEquals(true, headers.get("Allow").contains(method));
        }
    }

    @Test
    public void getResponseDataReturnsAResponseWithAllowHeaderWithoutMethodsNotAllowedForPath() {
        OptionsResourceHandler endpoint = new OptionsResourceHandler();
        AbstractHttpRequest request = new HTTPRequest("GET /method_options2 HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);
        HashMap<String, String> headers = HeaderParser.parse(response.get("headers"));
        String[] disallowedMethods = {"POST", "HEAD", "PUT"};

        for (String method : disallowedMethods) {
            assertEquals(false, headers.get("Allow").contains(method));
        }

    }
}

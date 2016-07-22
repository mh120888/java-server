package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;
import org.junit.Assert;
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
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        String response = endpoint.getResponse(request, new HTTPResponse());

        Assert.assertTrue(response.contains("HTTP/1.1 200"));
    }

    @Test
    public void getReponseDataReturnsCorrectResponseLineForOPTIONS() {
        OptionsResourceHandler endpoint = new OptionsResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("OPTIONS /pathdoesnotmatter HTTP/1.1");
        String response = endpoint.getResponse(request, new HTTPResponse());

        Assert.assertTrue(response.contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturnsAResponseWithAnAllowHeader() {
        OptionsResourceHandler endpoint = new OptionsResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        String response = endpoint.getResponse(request, new HTTPResponse());

        Assert.assertTrue(response.contains("Allow: "));
    }

    @Test
    public void getResponseReturnsAResponseWithAllowHeaderWithMethodsAllowedForGivenPath() {
        OptionsResourceHandler endpoint = new OptionsResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /method_options HTTP/1.1");
        String response = endpoint.getResponse(request, new HTTPResponse());
        String[] acceptedMethods = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};

        for (String method : acceptedMethods) {
            Assert.assertTrue(response.contains(method));
        }
    }

    @Test
    public void getResponseReturnsAResponseWithAllowHeaderWithMethodsAllowedForADifferentPath() {
        OptionsResourceHandler endpoint = new OptionsResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /method_options2 HTTP/1.1");
        String response = endpoint.getResponse(request, new HTTPResponse());
        String[] acceptedMethods = {"GET", "OPTIONS"};

        for (String method : acceptedMethods) {
            Assert.assertTrue(response.contains(method));
        }
    }

    @Test
    public void getResponseReturnsAResponseWithAllowHeaderWithoutMethodsNotAllowedForPath() {
        OptionsResourceHandler endpoint = new OptionsResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /method_options2 HTTP/1.1");
        String response = endpoint.getResponse(request, new HTTPResponse());
        String[] disallowedMethods = {"POST", "HEAD", "PUT"};

        for (String method : disallowedMethods) {
            Assert.assertFalse(response.contains(method));
        }

    }
}

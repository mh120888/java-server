package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class PostableResourceHandlerTest {
//    @Test
//    public void getResponseDataReturnsA200forPOSTWithParams() {
//        cobspecapp.PostableResourceHandler endpoint = new cobspecapp.PostableResourceHandler();
//        HashMap<String, String> request = server.HTTPRequestParser.parse("POST /form HTTP/1.1\n\nsomeParam=someValue");
//        HashMap<String, String> response = endpoint.getResponseData(request);
//
//        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
//    }

    @Test
    public void getResponseDataReturns200ForGET() {
        PostableResourceHandler endpoint = new PostableResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /form HTTP/1.1");
        String response = endpoint.getResponseData(request, new HTTPResponse());

        Assert.assertTrue(response.contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseDataReturnsA405ForPostWithoutParams() {
        PostableResourceHandler endpoint = new PostableResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("POST /form HTTP/1.1");
        String response = endpoint.getResponseData(request, new HTTPResponse());

        Assert.assertTrue(response.contains("HTTP/1.1 405"));
    }

//    @Test
//    public void getResponseDataReturnsCorrectBodyContentForPostWithParams() {
//        cobspecapp.PostableResourceHandler endpoint = new cobspecapp.PostableResourceHandler();
//        endpoint.getResponseData(server.HTTPRequestParser.parse("POST /form HTTP/1.1\n\ndata=something"));
//        HashMap<String, String> request = server.HTTPRequestParser.parse("GET /form HTTP/1.1");
//        HashMap<String, String> response = endpoint.getResponseData(request);
//
//        assertEquals("data=something", response.get("body"));
//    }
}
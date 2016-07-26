package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class PostableActionTest {
//    @Test
//    public void getResponseReturnsA200forPOSTWithParams() {
//        cobspecapp.PostableAction endpoint = new cobspecapp.PostableAction();
//        HashMap<String, String> request = server.HTTPRequestParser.parse("POST /form HTTP/1.1\n\nsomeParam=someValue");
//        HashMap<String, String> response = endpoint.getResponse(request);
//
//        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
//    }

    @Test
    public void getResponseReturns200ForGET() {
        PostableAction endpoint = new PostableAction();
        AbstractHTTPRequest request = new HTTPRequest("GET /form HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        Assert.assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturnsA405ForPostWithoutParams() {
        PostableAction endpoint = new PostableAction();
        AbstractHTTPRequest request = new HTTPRequest("POST /form HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        Assert.assertTrue(response.getFormattedResponse().contains("HTTP/1.1 405"));
    }

//    @Test
//    public void getResponseReturnsCorrectBodyContentForPostWithParams() {
//        cobspecapp.PostableAction endpoint = new cobspecapp.PostableAction();
//        endpoint.getResponse(server.HTTPRequestParser.parse("POST /form HTTP/1.1\n\ndata=something"));
//        HashMap<String, String> request = server.HTTPRequestParser.parse("GET /form HTTP/1.1");
//        HashMap<String, String> response = endpoint.getResponse(request);
//
//        assertEquals("data=something", response.get("body"));
//    }
}
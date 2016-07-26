package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/11/16.
 */

public class PostableActionTest {
    @Test
    public void getResponseReturnsA200forPOST() {
        PostableAction endpoint = new PostableAction();
        AbstractHTTPRequest request = new HTTPRequest("POST /form HTTP/1.1");

        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200 OK"));
    }

    @Test
    public void getResponseReturnsA200ForPUT() {
        PostableAction endpoint = new PostableAction();
        AbstractHTTPRequest request = new HTTPRequest("PUT /form HTTP/1.1");

        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200 OK"));
    }

    @Test
    public void GETRequestsIncludeTheStaticVariableDataInBody() {
        PostableAction endpoint = new PostableAction();
        String fakeData = "some random data";
        endpoint.data = fakeData;

        AbstractHTTPRequest request = new HTTPRequest("GET /form HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        Assert.assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }
    @Test
    public void POSTRequestsDoNotContainStaticVarDataInBody() {
        PostableAction endpoint = new PostableAction();
        String fakeData = "some random data";
        endpoint.data = fakeData;

        AbstractHTTPRequest request = new HTTPRequest("POST /form HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        Assert.assertFalse(response.getFormattedResponse().contains("some random data"));
    }

    @Test
    public void POSTRequestWithDataChangesTheValueOfStaticVarData() {
        PostableAction endpoint = new PostableAction();

        AbstractHTTPRequest request = new HTTPRequest("POST /form HTTP/1.1\n\ndata=fatcat");

        assertEquals("data=fatcat", endpoint.data);
    }
}
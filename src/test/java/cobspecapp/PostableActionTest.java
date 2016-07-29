package cobspecapp;

import request.Request;
import response.Response;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/11/16.
 */

public class PostableActionTest {
    PostableAction action;

    @Before
    public void setup() {
        action = new PostableAction();
    }

    @Test
    public void getResponseReturnsA200forPOST() {
        Response response = ResponseGenerator.generateResponse("POST /form HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200 OK"));
    }

    @Test
    public void getResponseReturnsA200ForPUT() {
        Response response = ResponseGenerator.generateResponse("PUT /form HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200 OK"));
    }

    @Test
    public void GETRequestsIncludeTheStaticVariableDataInBody() {
        action.data = "some random data";

        Response response = ResponseGenerator.generateResponse("GET /form HTTP/1.1", action);
        Assert.assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }
    @Test
    public void POSTRequestsDoNotContainStaticVarDataInBody() {
        PostableAction endpoint = new PostableAction();
        String fakeData = "some random data";
        endpoint.data = fakeData;

        Response response = ResponseGenerator.generateResponse("POST /form HTTP/1.1", action);

        Assert.assertFalse(response.getFormattedResponse().contains(fakeData));
    }

    @Test
    public void POSTRequestWithDataChangesTheValueOfStaticVarData() {
        Request request = new HTTPRequest("POST /form HTTP/1.1");
        request.setBody("random stuff here");

        Response response = action.getResponse(request, new HTTPResponse());

        assertEquals("random stuff here", action.data);
    }
}
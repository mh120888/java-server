package cobspecapp;

import basichttpmessage.BasicHTTPMessageFactory;
import mocks.MockHTTPRequest;
import httpmessage.HTTPResponse;
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
        HTTPResponse response = ResponseGenerator.generateResponse("POST", "/form", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200 OK"));
    }

    @Test
    public void getResponseReturnsA200ForPUT() {
        HTTPResponse response = ResponseGenerator.generateResponse("PUT", "/form", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200 OK"));
    }

    @Test
    public void GETRequestsIncludeTheStaticVariableDataInBody() {
        action.data = "some random data";

        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/form", action);
        Assert.assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }
    @Test
    public void POSTRequestsDoNotContainStaticVarDataInBody() {
        PostableAction endpoint = new PostableAction();
        String fakeData = "some random data";
        endpoint.data = fakeData;

        HTTPResponse response = ResponseGenerator.generateResponse("POST", "/form", action);

        Assert.assertFalse(response.getFormattedResponse().contains(fakeData));
    }

    @Test
    public void POSTRequestWithDataChangesTheValueOfStaticVarData() {
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("POST");
        request.setPathWithParams("/form");
        request.setBody("random stuff here");

        action.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        assertEquals("random stuff here", action.data);
    }
}
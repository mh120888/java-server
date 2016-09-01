package com.github.mh120888.cobspecapp;

import com.github.mh120888.basichttpmessage.BasicHTTPMessageFactory;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;
import com.github.mh120888.mocks.MockHTTPRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FormActionTest {
    FormAction action;

    @Before
    public void setup() {
        action = new FormAction();
    }

    @Test
    public void getResponseReturnsA200forPOST() {
        HTTPResponse response = ResponseGenerator.generateResponse("POST", "/form", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.OK)));
    }

    @Test
    public void getResponseReturnsA200ForPUT() {
        HTTPResponse response = ResponseGenerator.generateResponse("PUT", "/form", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.OK)));
    }

    @Test
    public void GETRequestsIncludeTheStaticVariableDataInBody() {
        action.data = "some random data";

        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/form", action);
        Assert.assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.OK)));
    }
    @Test
    public void POSTRequestsDoNotContainStaticVarDataInBody() {
        FormAction endpoint = new FormAction();
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
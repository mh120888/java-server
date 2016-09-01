package com.github.mh120888.cobspecapp;

import com.github.mh120888.basichttpmessage.BasicHTTPMessageFactory;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;
import com.github.mh120888.mocks.MockHTTPRequest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ParametersActionTest {
    ParametersAction action;

    @Before
    public void setup() {
        action = new ParametersAction();
    }

    @Test
    public void GETRequestReturns200() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/parameters", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.OK)));
    }

    @Test
    public void POSTRequestReturns405() {
        HTTPResponse response = ResponseGenerator.generateResponse("POST", "/parameters", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.METHOD_NOT_ALLOWED)));
    }

    @Test
    public void GETRequestWithParametersIncludesParametersInBody() {
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/parameters");
        request.addParam("someParam", "newValue");
        HTTPResponse response = action.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        assertTrue(response.getFormattedResponse().contains("someParam = newValue"));
    }
}
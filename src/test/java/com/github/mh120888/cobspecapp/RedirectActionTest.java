package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RedirectActionTest {
    RedirectAction action;

    @Before
    public void setup() {
        action = new RedirectAction();
    }

    @Test
    public void getResponseReturnsA302Response() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/pathdoesnotmatter", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.FOUND)));
    }

    @Test
    public void getResponseReturnsResponseContainingLocationHeader() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/pathdoesnotmatter", action);

        assertTrue(response.getFormattedResponse().contains(HTTPHeaders.LOCATION));
    }

    @Test
    public void getResponseReturnsResponseContainingLocationHeaderWithCorrectValue() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/pathdoesnotmatter", action);

        assertTrue(response.getFormattedResponse().contains(HTTPHeaders.LOCATION + ": http://localhost:5000/"));
    }
}



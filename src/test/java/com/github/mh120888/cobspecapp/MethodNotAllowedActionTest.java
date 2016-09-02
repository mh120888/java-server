package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MethodNotAllowedActionTest {
    MethodNotAllowedAction action;

    @Before
    public void setUp() throws Exception {
        action = new MethodNotAllowedAction();
    }

    @Test
    public void returns405() {
        HTTPResponse response = ResponseGenerator.generateResponse("ANYTHING", "/anything", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.METHOD_NOT_ALLOWED)));
    }
}
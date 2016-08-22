package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPResponse;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NotFoundActionTest {
    @Test
    public void returnsAStatusOf404() {
        NotFoundAction action = new NotFoundAction();
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/foobar HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 404"));
    }
}
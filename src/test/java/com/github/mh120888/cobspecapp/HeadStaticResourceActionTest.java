package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HeadStaticResourceActionTest {
    public static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";
    HeadStaticResourceAction action;

    @Before
    public void setUp() throws Exception {
        action = new HeadStaticResourceAction(publicDirectory);
    }

    @Test
    public void getResponseReturns200() {
        HTTPResponse response = ResponseGenerator.generateResponse("HEAD",  "/", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.OK)));
    }

    @Test
    public void getResponseReturnsNoBody() {
        HTTPResponse response = ResponseGenerator.generateResponse("HEAD",  "/", action);
        String responseBodyAsString =  new String(response.getBody());

        assertTrue(responseBodyAsString.isEmpty());
    }
}
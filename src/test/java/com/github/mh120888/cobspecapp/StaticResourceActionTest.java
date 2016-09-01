package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertTrue;

public class StaticResourceActionTest {
    public static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";
    StaticResourceAction action;

    @Before
    public void setUp() throws Exception {
        action = new StaticResourceAction(publicDirectory);
    }

//    @Test
//    public void getResponseReturns200ForHeadRequest() {
//        HTTPResponse response = ResponseGenerator.generateResponse("HEAD",  "/", action);
//
//        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.OK)));
//    }

    @Test
    public void getResponseReturns405ForInvalidMethods() {
        HTTPResponse response = ResponseGenerator.generateResponse("NOTAREALMETHOD", "/", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.METHOD_NOT_ALLOWED)));
    }

    @Test
    public void postRequestWithNoParamsReturnsA405() {
        HTTPResponse response = ResponseGenerator.generateResponse("POST", "/", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.METHOD_NOT_ALLOWED)));
    }

    @Test
    public void getCorrectPortionOfFileContentsReturnsTheCorrectPortionOfFile() {
        byte[] fullContents = "All of the contents".getBytes();
        int[] range = {0, 5};

        Assert.assertEquals("All of", new String(action.getCorrectPortionOfFileContents(fullContents, range)));
    }
}
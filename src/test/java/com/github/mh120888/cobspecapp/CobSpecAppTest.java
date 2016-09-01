package com.github.mh120888.cobspecapp;

import com.github.mh120888.basichttpmessage.BasicHTTPMessageFactory;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.mocks.MockHTTPRequest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CobSpecAppTest {
    @Test
    public void getResponseReturnsAResponseWithCorrectHTTPVersion() {
        CobSpecApp app = new CobSpecApp("/");
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/");
        request.setVersion("HTTP/1.0");

        HTTPResponse response = app.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.0"));
    }
}
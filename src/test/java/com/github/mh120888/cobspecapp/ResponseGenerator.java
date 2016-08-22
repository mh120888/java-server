package com.github.mh120888.cobspecapp;

import com.github.mh120888.basichttpmessage.BasicHTTPMessageFactory;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.mocks.MockHTTPRequest;

public class ResponseGenerator {
    static HTTPResponse generateResponse(String method, String pathWithParams, Action action) {
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod(method);
        request.setPathWithParams(pathWithParams);
        request.setVersion("HTTP/1.1");
        return action.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());
    }
}

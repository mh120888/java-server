package com.github.mh120888.cobspecapp;

import com.github.mh120888.app.Application;
import com.github.mh120888.basichttpmessage.BasicHTTPMessageFactory;
import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.mocks.MockHTTPRequest;

public class ResponseGenerator {
    static HTTPResponse generateResponse(String method, String pathWithParams, Application action) {
        MockHTTPRequest request = new MockHTTPRequest();
        HTTPResponse response = new BasicHTTPMessageFactory().getNewResponse();
        request.setMethod(method);
        request.setPathWithParams(pathWithParams);
        request.setVersion("HTTP/1.1");
        request.addHeader(HTTPHeaders.HOST, "localhost:5000");
        response.setHTTPVersion(request.getVersion());
        return action.getResponse(request, response);
    }
}

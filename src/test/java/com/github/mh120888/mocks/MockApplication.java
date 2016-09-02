package com.github.mh120888.mocks;

import com.github.mh120888.app.Application;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;

public class MockApplication implements Application {
    public String defaultBody = "999 Not a Real Status Code";

    public MockApplication() {}

    public MockApplication(String fakeResponse) {
        defaultBody = fakeResponse;
    }

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setHTTPVersion("HTTP/1.1");
        response.setStatus(999);
        response.setBody(defaultBody.getBytes());
        return response;
    }
}

package mocks;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;
import app.Application;

public class MockApplication implements Application {
    public String defaultBody = "999 Not a Real Status Code";

    public MockApplication() {};
    public MockApplication(String fakeResponse) {
        defaultBody = fakeResponse;
    }

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setHTTPVersion("HTTP/1.1");
        response.setStatus(999);
        response.setBody(defaultBody.getBytes());
        return response;
    };
}

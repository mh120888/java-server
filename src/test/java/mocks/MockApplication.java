package mocks;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import app.Application;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public class MockApplication extends Application {
    public String defaultBody = "999 Not a Real Status Code";

    public MockApplication() {};
    public MockApplication(String fakeResponse) {
        defaultBody = fakeResponse;
    }

    public AbstractHTTPResponse getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion("HTTP/1.1");
        response.setStatus(999);
        response.setBodyFromString(defaultBody);
        return response;
    };
}

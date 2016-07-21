package mocks;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import app.Application;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public class MockApplication extends Application {
    public String defaultResponse = "999 Not a Real Status Code";

    public MockApplication() {};
    public MockApplication(String fakeResponse) {
        defaultResponse = fakeResponse;
    }

    public String getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        return defaultResponse;
    };
}

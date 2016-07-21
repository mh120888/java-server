package mocks;

import abstracthttprequest.AbstractHTTPRequest;
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

    public HashMap<String, String> getResponse(AbstractHTTPRequest request) {
        HashMap<String, String> response = new HashMap<>();
        response.put("responseLine", defaultResponse);
        return response;
    };
}

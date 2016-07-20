package mocks;

import abstracthttprequest.AbstractHttpRequest;
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

    public HashMap<String, String> getResponse(AbstractHttpRequest request) {
        HashMap<String, String> response = new HashMap<>();
        response.put("responseLine", defaultResponse);
        return response;
    };
}

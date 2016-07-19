package mocks;

import app.Application;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public class MockApplication extends Application {
    public String defaultResponse;

    public MockApplication(String fakeResponse) {
        defaultResponse = fakeResponse;
    }

    public HashMap<String, String> getResponse(HashMap<String, String> request) {
        HashMap<String, String> response = new HashMap<>();
        if (!defaultResponse.isEmpty()) {
            response.put("responseLine", defaultResponse);
        } else {
            response.put("responseLine", "999 Not a Real Status Code");
        }
        return response;
    };
}

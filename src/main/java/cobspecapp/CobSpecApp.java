package cobspecapp;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;
import app.Application;

public class CobSpecApp implements Application {
    public String publicDirectory;

    public CobSpecApp(String filepath) {
        publicDirectory = filepath;
    }

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        Action action = Router.route(request, publicDirectory);
        return action.getResponse(request, response);
    }
}


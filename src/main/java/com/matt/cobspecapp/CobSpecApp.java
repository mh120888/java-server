package cobspecapp;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;
import app.Application;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public class CobSpecApp extends Application {
    public String publicDirectory;

    public CobSpecApp(String filepath) {
        publicDirectory = filepath;
    }

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        Action action = Router.route(request, publicDirectory);
        return action.getResponse(request, response);
    }
}


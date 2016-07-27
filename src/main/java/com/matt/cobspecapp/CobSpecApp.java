package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import app.Application;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public class CobSpecApp extends Application {
    public String publicDirectory;

    public CobSpecApp(String filepath) {
        publicDirectory = filepath;
    }

    public AbstractHTTPResponse getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        Action action = Router.route(request, publicDirectory);
        AbstractHTTPResponse result = action.getResponse(request, response);
        return result;
    }
}


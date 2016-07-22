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

    public String getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);
        String responseData = resourceHandler.getResponse(request, response);
        return responseData;
    }
}


package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import app.Application;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public class CobSpecApp extends Application {
    public String publicDirectory;

    public CobSpecApp(String filepath) {
        publicDirectory = filepath;
    }

    public HashMap<String, String> getResponse(AbstractHTTPRequest request) {
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);
        HashMap<String, String> responseData = resourceHandler.getResponseData(request);
        return responseData;
    }
}


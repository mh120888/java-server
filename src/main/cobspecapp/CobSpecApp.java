package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import app.Application;
import httpresponse.HTTPResponse;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public class CobSpecApp extends Application {
    public String publicDirectory;

    public CobSpecApp(String filepath) {
        publicDirectory = filepath;
    }

//    public HashMap<String, String> getResponse(AbstractHTTPRequest request) {
//        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);
//        HashMap<String, String> responseData = resourceHandler.getResponseData(request);
//        return responseData;
//    }

    public String getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);
        String responseData = resourceHandler.getResponseData(request, response);
        return responseData;
    }
}


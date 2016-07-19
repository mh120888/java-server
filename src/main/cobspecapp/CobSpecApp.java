package cobspecapp;

import app.Application;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public class CobSpecApp extends Application {
    public HashMap<String, String> getResponse(HashMap<String, String> request) {
        ResourceHandler resourceHandler = Router.getEndpoint(request);
        HashMap<String, String> responseData = resourceHandler.getResponseData(request);
        return responseData;
    }
}


package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import response.Response;
import app.Application;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public class CobSpecApp extends Application {
    public String publicDirectory;

    public CobSpecApp(String filepath) {
        publicDirectory = filepath;
    }

    public Response getResponse(AbstractHTTPRequest request, Response response) {
        Action action = Router.route(request, publicDirectory);
        Response result = action.getResponse(request, response);
        return result;
    }
}


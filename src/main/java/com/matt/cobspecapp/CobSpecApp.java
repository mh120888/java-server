package cobspecapp;

import request.Request;
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

    public Response getResponse(Request request, Response response) {
        Action action = Router.route(request, publicDirectory);
        return action.getResponse(request, response);
    }
}


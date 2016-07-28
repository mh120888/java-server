package cobspecapp;

import request.Request;
import response.Response;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class CoffeeAction implements Action {
    public CoffeeAction() {}

    public Response getResponse(Request request, Response response) {
        response.setHTTPVersion(request.getVersion());
        if (request.getPath().equals("/coffee")) {
            response.setStatus(418);
            response.setBody("I'm a teapot".getBytes());
        } else {
            response.setStatus(200);
        }
        return response;
    }
}

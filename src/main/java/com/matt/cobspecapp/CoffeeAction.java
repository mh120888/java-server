package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import response.Response;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class CoffeeAction implements Action {
    public CoffeeAction() {}

    public Response getResponse(AbstractHTTPRequest request, Response response) {
        response.setHTTPVersion(request.getVersion());
        if (request.getPath().equals("/coffee")) {
            response.setStatus(418);
            response.setBodyFromString("I'm a teapot");
        } else {
            response.setStatus(200);
        }
        return response;
    }
}

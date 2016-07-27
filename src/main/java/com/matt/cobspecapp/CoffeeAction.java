package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class CoffeeAction implements Action {
    public CoffeeAction() {}

    public AbstractHTTPResponse getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response) {
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

package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class CoffeeResourceHandler implements ResourceHandler {
    public CoffeeResourceHandler() {}

    public String getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        if (request.getPath().equals("/coffee")) {
            response.setStatus(418);
            response.setBody("I'm a teapot");
        } else {
            response.setStatus(200);
        }
        return response.getFormattedResponse();
    }
}

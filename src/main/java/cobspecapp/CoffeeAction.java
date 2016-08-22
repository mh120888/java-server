package cobspecapp;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;

public class CoffeeAction implements Action {
    public CoffeeAction() {}

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
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

package cobspecapp;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;

public class RedirectAction implements Action {
    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(302);
        response.addHeader("location", request.getBaseLocation());

        return response;
    }
}

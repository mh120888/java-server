package cobspecapp;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class RedirectAction implements Action {
    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(302);
        response.addHeader("location", request.getBaseLocation());

        return response;
    }
}

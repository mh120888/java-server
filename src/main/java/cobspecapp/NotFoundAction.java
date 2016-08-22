package cobspecapp;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;

public class NotFoundAction implements Action {
    public NotFoundAction() {}

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(404);

        return response;
    }
}

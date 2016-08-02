package cobspecapp;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class PostableAction implements Action {
    static String data = "default";

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(200);

        if (request.getMethod().equals("GET")) {
            response.setBody(data.getBytes());
        } else {
            data = request.getBody().trim();
        }

        return response;
    }
}
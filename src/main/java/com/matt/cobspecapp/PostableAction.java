package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import response.Response;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class PostableAction implements Action {
    static String data = "default";

    public Response getResponse(AbstractHTTPRequest request, Response response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(200);

        if (request.getMethod().equals("GET")) {
            response.setBodyFromString(data);
        } else {
            data = request.getBody().trim();
        }

        return response;
    }
}
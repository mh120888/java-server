package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import response.Response;


/**
 * Created by matthewhiggins on 7/11/16.
 */
public class NotFoundAction implements Action {
    public NotFoundAction() {}

    public Response getResponse(AbstractHTTPRequest request, Response response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(404);

        return response;
    }
}

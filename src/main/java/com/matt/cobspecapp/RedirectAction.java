package cobspecapp;

import request.Request;
import response.Response;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class RedirectAction implements Action {
    public Response getResponse(Request request, Response response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(302);
        response.addHeader("location", request.getBaseLocation());

        return response;
    }
}

package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;


/**
 * Created by matthewhiggins on 7/11/16.
 */
public class NotFoundAction implements Action {
    public NotFoundAction() {}

    public AbstractHTTPResponse getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(404);

        return response;
    }
}

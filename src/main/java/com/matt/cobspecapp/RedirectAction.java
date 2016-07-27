package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class RedirectAction implements Action {
    public AbstractHTTPResponse getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(302);
        response.addHeader("location", request.getBaseLocation());

        return response;
    }
}

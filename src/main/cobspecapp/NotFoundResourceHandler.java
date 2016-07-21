package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class NotFoundResourceHandler implements ResourceHandler {
    public NotFoundResourceHandler() {}

    public String getResponseData(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(404);

        return response.getFormattedResponse();
    }
}

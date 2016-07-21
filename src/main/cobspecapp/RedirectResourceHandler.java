package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class RedirectResourceHandler implements ResourceHandler {
    public String getResponseData(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(302);
        response.addHeader("location", request.getBaseLocation());

        return response.getFormattedResponse();
    }
}

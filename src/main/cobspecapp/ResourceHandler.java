package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public interface ResourceHandler {
    AbstractHTTPResponse getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response);
}

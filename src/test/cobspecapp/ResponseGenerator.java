package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;

/**
 * Created by matthewhiggins on 7/26/16.
 */
public class ResponseGenerator {
    static AbstractHTTPResponse generateResponse(String requestLine, Action action) {
        AbstractHTTPRequest request = new HTTPRequest(requestLine);
        AbstractHTTPResponse response = action.getResponse(request, new HTTPResponse());
        return response;
    }
}

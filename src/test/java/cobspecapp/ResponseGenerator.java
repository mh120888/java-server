package cobspecapp;

import request.Request;
import response.Response;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;

/**
 * Created by matthewhiggins on 7/26/16.
 */
public class ResponseGenerator {
    static Response generateResponse(String requestLine, Action action) {
        Request request = new HTTPRequest(requestLine);
        Response response = action.getResponse(request, new HTTPResponse());
        return response;
    }
}

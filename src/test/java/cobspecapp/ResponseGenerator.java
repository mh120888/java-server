package cobspecapp;

import mocks.MockHTTPRequest;
import response.Response;
import httpresponse.HTTPResponse;

/**
 * Created by matthewhiggins on 7/26/16.
 */
public class ResponseGenerator {
    static Response generateResponse(String method, String pathWithParams, Action action) {
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod(method);
        request.setPathWithParams(pathWithParams);
        request.setVersion("HTTP/1.1");
        return action.getResponse(request, new HTTPResponse());
    }
}

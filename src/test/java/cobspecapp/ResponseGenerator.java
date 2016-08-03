package cobspecapp;

import basichttpmessage.BasicHTTPMessageFactory;
import mocks.MockHTTPRequest;
import httpmessage.HTTPResponse;

/**
 * Created by matthewhiggins on 7/26/16.
 */
public class ResponseGenerator {
    static HTTPResponse generateResponse(String method, String pathWithParams, Action action) {
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod(method);
        request.setPathWithParams(pathWithParams);
        request.setVersion("HTTP/1.1");
        return action.getResponse(request, new BasicHTTPMessageFactory.HTTPResponse());
    }
}

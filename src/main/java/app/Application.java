package app;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public interface Application {
    HTTPResponse getResponse(HTTPRequest request, HTTPResponse response);
}

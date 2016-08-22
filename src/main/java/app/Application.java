package app;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;

public interface Application {
    HTTPResponse getResponse(HTTPRequest request, HTTPResponse response);
}

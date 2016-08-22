package cobspecapp;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;

public interface Action {
    HTTPResponse getResponse(HTTPRequest request, HTTPResponse response);
}
package cobspecapp;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public interface Action {
    HTTPResponse getResponse(HTTPRequest request, HTTPResponse response);
}
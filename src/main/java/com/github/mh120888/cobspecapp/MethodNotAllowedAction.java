package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;

public class MethodNotAllowedAction implements Action {
    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        return response;
    }
}

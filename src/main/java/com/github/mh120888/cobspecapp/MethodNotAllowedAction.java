package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

public class MethodNotAllowedAction implements Action {
    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setStatus(HTTPStatus.METHOD_NOT_ALLOWED);
        return response;
    }
}

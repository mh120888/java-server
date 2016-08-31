package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

public class TeaAction implements Action {

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setStatus(HTTPStatus.OK);
        return response;
    }
}

package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

public class CoffeeAction implements Action {
    public CoffeeAction() {}

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setStatus(HTTPStatus.IM_A_TEAPOT);
        response.setBody("I'm a teapot".getBytes());
        return response;
    }
}

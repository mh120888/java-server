package com.github.mh120888.cobspecapp;

import com.github.mh120888.app.Application;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

public class FormAction implements Application {
    static String data = "default";

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setStatus(HTTPStatus.OK);

        if (request.getMethod().equals("GET")) {
            response.setBody(data.getBytes());
        } else {
            data = request.getBody().trim();
        }

        return response;
    }
}
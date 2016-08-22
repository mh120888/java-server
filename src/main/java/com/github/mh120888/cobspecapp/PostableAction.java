package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;

public class PostableAction implements Action {
    static String data = "default";

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(200);

        if (request.getMethod().equals("GET")) {
            response.setBody(data.getBytes());
        } else {
            data = request.getBody().trim();
        }

        return response;
    }
}
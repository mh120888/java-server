package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;

import java.util.Map;

public class ParametersAction implements Action {

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        if (request.getMethod().equals("GET")) {
            response.setStatus(200);
        } else {
            response.setStatus(405);
        }

        Map<String, String> parameters = request.getAllParams();

        if (parameters.size() > 0) {
            String body = "";
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                body += entry.getKey() + " = " + entry.getValue() + "\n";
            }
            response.setBody(body.getBytes());
        }

        return response;
    }
}

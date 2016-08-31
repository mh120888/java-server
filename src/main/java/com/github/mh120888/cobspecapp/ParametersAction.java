package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

import java.util.Map;

public class ParametersAction implements Action {
    private HTTPRequest request;
    private HTTPResponse response;

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        this.request = request;
        this.response = response;

        response.setStatus(getStatus());
        response.setBody(getBody());

        return response;
    }

    private int getStatus() {
        if (request.getMethod().equals("GET")) {
            return HTTPStatus.OK;
        } else {
            return 405;
        }
    }

    private byte[] getBody() {
        Map<String, String> parameters = request.getAllParams();

        String body = "";
        if (areThereAnyParameters(parameters)) {
            body = formatParametersForBody(parameters);
        }
        return body.getBytes();
    }

    private String formatParametersForBody(Map<String, String> parameters) {
        String body = "";
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            body += entry.getKey() + " = " + entry.getValue() + System.lineSeparator();
        }
        return body;
    }

    private boolean areThereAnyParameters(Map<String, String> parameters) {
        return parameters.size() > 0;
    }
}

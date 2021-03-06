package com.github.mh120888.cobspecapp;

import com.github.mh120888.app.Application;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

import java.util.Map;

import static com.github.mh120888.httpmessage.MessageFormatting.CRLF;

public class ParametersAction implements Application {
    private HTTPRequest request;

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        this.request = request;

        response.setStatus(getStatus());
        response.setBody(getBody());

        return response;
    }

    private int getStatus() {
        if (request.getMethod().equals("GET")) {
            return HTTPStatus.OK;
        } else {
            return HTTPStatus.METHOD_NOT_ALLOWED;
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
            body += entry.getKey() + " = " + entry.getValue() + CRLF;
        }
        return body;
    }

    private boolean areThereAnyParameters(Map<String, String> parameters) {
        return parameters.size() > 0;
    }
}

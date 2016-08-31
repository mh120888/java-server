package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

public class LogsAction implements Action {
    private String correctCredentials = "admin:hunter2";

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        if (isAuthorized(request)) {
            setAuthorizedResponse(response);
        } else {
            setUnauthorizedResponse(response);
        }
        return response;
    }

    private void setUnauthorizedResponse(HTTPResponse response) {
        response.setStatus(HTTPStatus.NOT_AUTHORIZED);
        response.addHeader(HTTPHeaders.WWW_AUTHENTICATE, "Basic realm=\"User Visible Realm\"");
    }

    private void setAuthorizedResponse(HTTPResponse response) {
        response.setStatus(HTTPStatus.OK);
        response.setBody(String.join(System.lineSeparator(), Logger.getLog()).getBytes());
    }

    private boolean isAuthorized(HTTPRequest request) {
        String encodedCredentials = "";
        if (request.containsHeader(HTTPHeaders.AUTHORIZATION)) {
            encodedCredentials = request.getHeader(HTTPHeaders.AUTHORIZATION).replace("Basic ", "");
        }
        return BasicAuthorizer.isAuthorized(encodedCredentials, correctCredentials);
    }
}

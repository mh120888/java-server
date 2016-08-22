package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;

public class LogsAction implements Action {
    private String correctCredentials = "admin:hunter2";

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setHTTPVersion(request.getVersion());

        if (isAuthorized(request)) {
            response.setStatus(200);
            response.setBody(String.join("\n", Logger.getLog()).getBytes());
        } else {
            response.setStatus(401);
            response.addHeader("WWW-Authenticate", "Basic realm=\"User Visible Realm\"");
        }
        return response;
    }

    public boolean isAuthorized(HTTPRequest request) {
        String encodedCredentials = "";
        if (request.containsHeader("Authorization")) {
            encodedCredentials = request.getHeader("Authorization").replace("Basic ", "");
        }
        return BasicAuthorizer.isAuthorized(encodedCredentials, correctCredentials);
    }
}

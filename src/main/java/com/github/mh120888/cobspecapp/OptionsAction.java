package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;

public class OptionsAction implements Action {

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(200);
        response.addHeader("Allow", buildHeaders(request.getPath()));

        return response;
    }

    private String buildHeaders(String path) {
        String header = "";
        header += String.join(",", acceptedMethods(path));
        return header;
    }

    private String[] acceptedMethods(String path) {
        switch (path) {
            case "/method_options": return new String[] {"GET","HEAD","POST","OPTIONS","PUT"};
            default:                return new String[] {"GET","OPTIONS"};
        }
    }
}

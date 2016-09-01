package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

import java.util.HashMap;

public class OptionsAction implements Action {

    private final static String[] defaultMethods = new String[0];
    private final static HashMap<String, String[]> permittedMethods = new HashMap<>();
    static {
        permittedMethods.put("/method_options", new String[] {"GET","HEAD","POST","OPTIONS","PUT"});
        permittedMethods.put("/method_options2", new String[] {"GET","OPTIONS"});
    }

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setStatus(HTTPStatus.OK);
        response.addHeader(HTTPHeaders.ALLOW, buildHeaders(request.getPath()));

        return response;
    }

    private String buildHeaders(String path) {
        String header = "";
        header += String.join(",", acceptedMethods(path));
        return header;
    }

    private String[] acceptedMethods(String path) {
        return permittedMethods.getOrDefault(path, defaultMethods);
    }
}

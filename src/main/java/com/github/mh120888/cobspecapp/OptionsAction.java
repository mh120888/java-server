package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

import java.util.List;

public class OptionsAction implements Action {
    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setStatus(HTTPStatus.OK);
        response.addHeader(HTTPHeaders.ALLOW, buildHeaders(request.getPath()));

        return response;
    }

    private String buildHeaders(String path) {
        String header = "";
        String[] methods = acceptedMethods(path);
        header += String.join(",", methods);
        return header;
    }

    private String[] acceptedMethods(String path) {
        List<String> listOfMethods = Router.getAllowedMethodsFor(path);

        String[] arrayOfMethods = new String[listOfMethods.size()];
        return listOfMethods.toArray(arrayOfMethods);
    }
}

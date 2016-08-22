package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.app.Application;

public class CobSpecApp implements Application {
    public String publicDirectory;

    public CobSpecApp(String filepath) {
        publicDirectory = filepath;
    }

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        Action action = Router.route(request, publicDirectory);
        return action.getResponse(request, response);
    }
}


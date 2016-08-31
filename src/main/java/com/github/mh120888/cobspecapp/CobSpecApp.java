package com.github.mh120888.cobspecapp;

import com.github.mh120888.app.Application;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;

public class CobSpecApp implements Application {
    public String publicDirectory;

    public CobSpecApp(String filepath) {
        publicDirectory = filepath;
    }

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        Action action = new Router(publicDirectory, new RealFileIO()).route(request);
        return action.getResponse(request, response);
    }
}


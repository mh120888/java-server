package com.github.mh120888.app;

import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;

public interface Application {
    HTTPResponse getResponse(HTTPRequest request, HTTPResponse response);
}

package com.github.mh120888.httpmessage;

public interface HTTPMessageFactory {
    HTTPRequest getNewRequest();
    HTTPResponse getNewResponse();
}

package com.github.mh120888.basichttpmessage;

import com.github.mh120888.httpmessage.HTTPMessageFactory;

public class BasicHTTPMessageFactory implements HTTPMessageFactory {
    public BasicHTTPRequest getNewRequest() {
        return new BasicHTTPRequest();
    }
    public BasicHTTPResponse getNewResponse() {
        return new BasicHTTPResponse();
    }
}

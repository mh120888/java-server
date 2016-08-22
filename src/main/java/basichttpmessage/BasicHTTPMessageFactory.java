package basichttpmessage;

import httpmessage.HTTPMessageFactory;

public class BasicHTTPMessageFactory implements HTTPMessageFactory {
    public BasicHTTPRequest getNewRequest() {
        return new BasicHTTPRequest();
    }
    public BasicHTTPResponse getNewResponse() {
        return new BasicHTTPResponse();
    }
}

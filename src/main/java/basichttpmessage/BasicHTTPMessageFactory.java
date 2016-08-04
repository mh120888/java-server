package basichttpmessage;

import httpmessage.HTTPMessageFactory;

/**
 * Created by matthewhiggins on 8/2/16.
 */
public class BasicHTTPMessageFactory implements HTTPMessageFactory {
    public BasicHTTPRequest getNewRequest() {
        return new BasicHTTPRequest();
    }
    public BasicHTTPResponse getNewResponse() {
        return new BasicHTTPResponse();
    }
}

package app;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public abstract class Application {
    public abstract HTTPResponse getResponse(HTTPRequest request, HTTPResponse response);
}

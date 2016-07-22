package app;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public abstract class Application {
    public abstract String getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response);
}

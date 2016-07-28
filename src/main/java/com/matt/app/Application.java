package app;

import abstracthttprequest.AbstractHTTPRequest;
import response.Response;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public abstract class Application {
    public abstract Response getResponse(AbstractHTTPRequest request, Response response);
}

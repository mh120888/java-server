package app;

import request.Request;
import response.Response;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public abstract class Application {
    public abstract Response getResponse(Request request, Response response);
}

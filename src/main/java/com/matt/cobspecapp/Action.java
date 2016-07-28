package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import response.Response;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public interface Action {
    Response getResponse(AbstractHTTPRequest request, Response response);
}
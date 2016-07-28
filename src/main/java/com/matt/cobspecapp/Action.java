package cobspecapp;

import request.Request;
import response.Response;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public interface Action {
    Response getResponse(Request request, Response response);
}
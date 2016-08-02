package httpresponse;

import response.ResponseFactory;

/**
 * Created by matthewhiggins on 8/2/16.
 */
public class HTTPResponseFactory implements ResponseFactory {
    public HTTPResponse getResponse() {
        return new HTTPResponse();
    }
}

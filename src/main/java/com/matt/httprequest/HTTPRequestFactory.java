package httprequest;

import request.RequestFactory;

/**
 * Created by matthewhiggins on 8/2/16.
 */
public class HTTPRequestFactory implements RequestFactory {
    public HTTPRequest getNewRequest() {
        return new HTTPRequest();
    }
}

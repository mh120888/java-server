package mocks;

import httprequest.HTTPRequest;
import request.RequestFactory;

/**
 * Created by matthewhiggins on 8/2/16.
 */
public class MockHTTPRequestFactory implements RequestFactory {
    public MockHTTPRequest getNewRequest() {
        return new MockHTTPRequest();
    }

}

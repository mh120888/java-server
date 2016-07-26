package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class ParametersResourceHandlerTest {
    @Test
    public void GETRequestReturns200() {
        ParametersResourceHandler endpoint = new ParametersResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /parameters HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void POSTRequestReturns405() {
        ParametersResourceHandler endpoint = new ParametersResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("POST /parameters HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 405"));
    }

    @Test
    public void GETRequestWithParametersIncludesParametersInBody() {
        ParametersResourceHandler endpoint = new ParametersResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /parameters?someParam=newValue HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("someParam = newValue"));
    }
}
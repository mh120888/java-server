package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import httprequest.HTTPRequest;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class ParametersResourceHandlerTest {
    @Test
    public void GETRequestReturns200() {
        ParametersResourceHandler endpoint = new ParametersResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /parameters HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

    @Test
    public void POSTRequestReturns405() {
        ParametersResourceHandler endpoint = new ParametersResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("POST /parameters HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 405 Method Not Allowed", response.get("responseLine"));
    }

    @Test
    public void GETRequestWithParametersIncludesParametersInBody() {
        ParametersResourceHandler endpoint = new ParametersResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /parameters?someParam=newValue HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertTrue(response.get("body").contains("someParam = newValue"));
    }
}
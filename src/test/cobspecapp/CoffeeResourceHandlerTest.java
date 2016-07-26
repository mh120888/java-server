package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class CoffeeResourceHandlerTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void returns418ForGETCoffee() {
        CoffeeResourceHandler endpoint = new CoffeeResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /coffee HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 418"));
    }

    @Test
    public void bodyContainsCorrectStringIfPathIsCoffee() {
        CoffeeResourceHandler endpoint = new CoffeeResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /coffee HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("I'm a teapot"));
    }

    @Test
    public void returns200ForGETTea() {
        CoffeeResourceHandler endpoint = new CoffeeResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /tea HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

}
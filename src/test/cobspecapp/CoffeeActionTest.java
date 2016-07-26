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
public class CoffeeActionTest {
    CoffeeAction action;

    @Before
    public void setUp() throws Exception {
        action = new CoffeeAction();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void returns418ForGETCoffee() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /coffee HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 418"));
    }

    @Test
    public void bodyContainsCorrectStringIfPathIsCoffee() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /coffee HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("I'm a teapot"));
    }

    @Test
    public void returns200ForGETTea() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /tea HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

}
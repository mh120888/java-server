package test;

import cobspecapp.CoffeeResourceHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.HTTPRequestParser;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
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
        HashMap<String, String> request = HTTPRequestParser.parse("GET /coffee HTTP/1.0");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 418", response.get("responseLine"));
    }

    @Test
    public void bodyContainsCorrectStringIfPathIsCoffee() {
        CoffeeResourceHandler endpoint = new CoffeeResourceHandler();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /coffee HTTP/1.0");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertTrue(response.get("body").contains("I'm a teapot"));
    }

    @Test
    public void returns200ForGETTea() {
        CoffeeResourceHandler endpoint = new CoffeeResourceHandler();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /tea HTTP/1.0");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

}
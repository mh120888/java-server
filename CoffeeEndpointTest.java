import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class CoffeeEndpointTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void returns418ForGETCoffee() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET /coffee HTTP/1.0");
        HashMap<String, String> response = CoffeeEndpoint.getResponseData(request);

        assertEquals("HTTP/1.1 418", response.get("responseLine"));
    }

    @Test
    public void bodyContainsCorrectStringIfPathIsCoffee() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET /coffee HTTP/1.0");
        HashMap<String, String> response = CoffeeEndpoint.getResponseData(request);

        assertTrue(response.get("body").contains("I'm a teapot"));
    }

    @Test
    public void returns200ForGETTea() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET /tea HTTP/1.0");
        HashMap<String, String> response = CoffeeEndpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

}
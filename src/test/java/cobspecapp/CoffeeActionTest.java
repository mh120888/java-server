package cobspecapp;

import httpmessage.HTTPResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

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
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/coffee", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 418"));
    }

    @Test
    public void bodyContainsCorrectStringIfPathIsCoffee() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/coffee", action);

        assertTrue(response.getFormattedResponse().contains("I'm a teapot"));
    }

    @Test
    public void returns200ForGETTea() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/tea", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

}
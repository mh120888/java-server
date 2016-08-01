package cobspecapp;

import response.Response;
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
        Response response = ResponseGenerator.generateResponse("GET", "/coffee", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 418"));
    }

    @Test
    public void bodyContainsCorrectStringIfPathIsCoffee() {
        Response response = ResponseGenerator.generateResponse("GET", "/coffee", action);

        assertTrue(response.getFormattedResponse().contains("I'm a teapot"));
    }

    @Test
    public void returns200ForGETTea() {
        Response response = ResponseGenerator.generateResponse("GET", "/tea", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

}
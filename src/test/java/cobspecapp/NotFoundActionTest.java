package cobspecapp;

import response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class NotFoundActionTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void returnsAStatusOf404() {
        NotFoundAction action = new NotFoundAction();
        Response response = ResponseGenerator.generateResponse("GET /foobar HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 404"));
    }

}
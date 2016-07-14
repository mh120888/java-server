import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class NotFoundResourceHandlerTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void returnsAStatusOf404() {
        NotFoundResourceHandler endpoint = new NotFoundResourceHandler();

        HashMap<String, String> request = HTTPRequestParser.parse("GET /foobar HTTP/1.0");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 404 Not Found", response.get("responseLine"));
    }

}
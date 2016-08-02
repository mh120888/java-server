package cobspecapp;

import httpmessage.HTTPResponse;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class RedirectActionTest {
    RedirectAction action;

    @Before
    public void setup() {
        action = new RedirectAction();
    }

    @Test
    public void getResponseReturnsA302Response() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/pathdoesnotmatter", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 302"));
    }

    @Test
    public void getResponseReturnsResponseContainingLocationHeader() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/pathdoesnotmatter", action);

        assertTrue(response.getFormattedResponse().contains("location: "));
    }

    @Test
    public void getResponseReturnsResponseContainingLocationHeaderWithCorrectValue() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/pathdoesnotmatter", action);

        assertTrue(response.getFormattedResponse().contains("location: http://localhost:5000/"));
    }
}



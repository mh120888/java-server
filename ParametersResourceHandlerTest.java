import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class ParametersResourceHandlerTest {
    @Test
    public void GETRequestReturns200() {
        ParametersResourceHandler endpoint = new ParametersResourceHandler();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /parameters HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

    @Test
    public void POSTRequestReturns405() {
        ParametersResourceHandler endpoint = new ParametersResourceHandler();
        HashMap<String, String> request = HTTPRequestParser.parse("POST /parameters HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 405 Method Not Allowed", response.get("responseLine"));
    }

    @Test
    public void GETRequestWithParametersIncludesParametersInBody() {
        ParametersResourceHandler endpoint = new ParametersResourceHandler();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /parameters?someParam=newValue HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertTrue(response.get("body").contains("someParam = newValue"));
    }
}
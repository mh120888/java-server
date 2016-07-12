import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class PostableEndpointTest {
    @Test
    public void getResponseDataReturnsA200forPOSTWithParams() {
        PostableEndpoint endpoint = new PostableEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("POST /form HTTP/1.1\n\nsomeParam=someValue");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

    @Test
    public void getResponseDataReturns200ForGET() {
        PostableEndpoint endpoint = new PostableEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /form HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

    @Test
    public void getResponseDataReturnsA405ForPostWithoutParams() {
        PostableEndpoint endpoint = new PostableEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("POST /form HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 405 Method Not Allowed", response.get("responseLine"));
    }

    @Test
    public void getResponseDataReturnsCorrectBodyContentForPostWithParams() {
        PostableEndpoint endpoint = new PostableEndpoint();
        endpoint.getResponseData(HTTPRequestParser.parse("POST /form HTTP/1.1\n\ndata=something"));
        HashMap<String, String> request = HTTPRequestParser.parse("GET /form HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("data=something", response.get("body"));
    }
}
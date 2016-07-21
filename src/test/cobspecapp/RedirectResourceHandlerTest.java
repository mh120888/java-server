package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import httprequest.HTTPRequest;
import org.junit.Test;
import server.HeaderParser;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class RedirectResourceHandlerTest {
    @Test
    public void getResponseDataReturnsA302Response() {
        RedirectResourceHandler endpoint = new RedirectResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 302 Found", response.get("responseLine"));
    }

    @Test
    public void getResponseDataReturnsResponseContainingLocationHeader() {
        RedirectResourceHandler endpoint = new RedirectResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);
        HashMap<String, String> headers = HeaderParser.parse(response.get("headers"));

        assertEquals(true, headers.containsKey("location"));
    }

    @Test
    public void getResponseDataReturnsResponseContainingLocationHeaderWithCorrectValue() {
        RedirectResourceHandler endpoint = new RedirectResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);
        HashMap<String, String> headers = HeaderParser.parse(response.get("headers"));

        assertEquals("http://localhost:5000/", headers.get("location"));
    }
}



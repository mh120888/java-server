package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;
import org.junit.Test;
import server.HeaderParser;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class RedirectResourceHandlerTest {
    @Test
    public void getResponseDataReturnsA302Response() {
        RedirectResourceHandler endpoint = new RedirectResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        String response = endpoint.getResponseData(request, new HTTPResponse());

        assertTrue(response.contains("HTTP/1.1 302"));
    }

    @Test
    public void getResponseDataReturnsResponseContainingLocationHeader() {
        RedirectResourceHandler endpoint = new RedirectResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        String response = endpoint.getResponseData(request, new HTTPResponse());

        assertTrue(response.contains("location: "));
    }

    @Test
    public void getResponseDataReturnsResponseContainingLocationHeaderWithCorrectValue() {
        RedirectResourceHandler endpoint = new RedirectResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        String response = endpoint.getResponseData(request, new HTTPResponse());

        assertTrue(response.contains("location: http://localhost:5000/"));
    }
}



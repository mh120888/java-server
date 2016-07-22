package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class RedirectResourceHandlerTest {
    @Test
    public void getResponseReturnsA302Response() {
        RedirectResourceHandler endpoint = new RedirectResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        String response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.contains("HTTP/1.1 302"));
    }

    @Test
    public void getResponseReturnsResponseContainingLocationHeader() {
        RedirectResourceHandler endpoint = new RedirectResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        String response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.contains("location: "));
    }

    @Test
    public void getResponseReturnsResponseContainingLocationHeaderWithCorrectValue() {
        RedirectResourceHandler endpoint = new RedirectResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        String response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.contains("location: http://localhost:5000/"));
    }
}



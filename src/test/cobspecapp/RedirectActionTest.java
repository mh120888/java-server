package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class RedirectActionTest {
    @Test
    public void getResponseReturnsA302Response() {
        RedirectAction endpoint = new RedirectAction();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 302"));
    }

    @Test
    public void getResponseReturnsResponseContainingLocationHeader() {
        RedirectAction endpoint = new RedirectAction();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("location: "));
    }

    @Test
    public void getResponseReturnsResponseContainingLocationHeaderWithCorrectValue() {
        RedirectAction endpoint = new RedirectAction();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("location: http://localhost:5000/"));
    }
}



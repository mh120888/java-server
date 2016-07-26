package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import httpresponse.HTTPResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

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
        AbstractHTTPRequest request = new httprequest.HTTPRequest("GET /foobar HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 404"));
    }

}
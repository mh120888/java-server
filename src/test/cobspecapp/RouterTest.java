package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import httprequest.HTTPRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class RouterTest {
    public static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";

    @Before
    public void setUp() throws Exception {
        Logger.clearLog();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getEndpointReturnsStaticResourceEndpointForAStaticResource() {
        AbstractHTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof StaticResourceHandler);
    }

    @Test
    public void getEndpointReturnsCoffeeEndpointWhenAppropriate() {
        AbstractHTTPRequest request = new HTTPRequest("GET /coffee HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof CoffeeResourceHandler);
    }

    @Test
    public void getEndpointReturnsNotFoundEndpointWhenPathIsNotRecognized() {
        AbstractHTTPRequest request = new HTTPRequest("GET /foobar HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof NotFoundResourceHandler);
    }

    @Test
    public void getEndpointReturnsPostableEndpointWhenAppropriate() {
        AbstractHTTPRequest request = new HTTPRequest("POST /form HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof PostableResourceHandler);
    }

    @Test
    public void getEndpointReturnsLogsEndpointWhenAppropriatePathIsRequested() {
        AbstractHTTPRequest request = new HTTPRequest("GET /logs HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof LogsResourceHandler);
    }

    @Test
    public void allRequestsAreLoggedAfterBeingRoutes() {
        String requestLine = "GET /logs HTTP/1.1";
        AbstractHTTPRequest request = new HTTPRequest(requestLine);
        Router.getEndpoint(request, publicDirectory);

        Assert.assertEquals(true, Logger.getLog().contains(requestLine));
    }

    @Test
    public void requestToParametersReturnsParametersEndpoint() {
        AbstractHTTPRequest request = new HTTPRequest("GET /parameters HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof ParametersResourceHandler);
    }

    @Test
    public void getEndpointReturnsOptionsEndPointWhenAppropriate() {
        AbstractHTTPRequest request = new HTTPRequest("GET /method_options HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof OptionsResourceHandler);
    }

    @Test
    public void getEndpointReturnsRedirectResourceHandlerWhenAppropriate() {
        AbstractHTTPRequest request = new HTTPRequest("GET /redirect HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof RedirectResourceHandler);
    }
}
package cobspecapp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.HTTPRequestParser;

import java.util.HashMap;

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
        HashMap<String, String> request = HTTPRequestParser.parse("GET / HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof StaticResourceHandler);
    }

    @Test
    public void getEndpointReturnsCoffeeEndpointWhenAppropriate() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET /coffee HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof CoffeeResourceHandler);
    }

    @Test
    public void getEndpointReturnsNotFoundEndpointWhenPathIsNotRecognized() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET /foobar HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof NotFoundResourceHandler);
    }

    @Test
    public void getEndpointReturnsPostableEndpointWhenAppropriate() {
        HashMap<String, String> request = HTTPRequestParser.parse("POST /form HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof PostableResourceHandler);
    }

    @Test
    public void getEndpointReturnsLogsEndpointWhenAppropriatePathIsRequested() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET /logs HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof LogsResourceHandler);
    }

    @Test
    public void allRequestsAreLoggedAfterBeingRoutes() {
        String requestLine = "GET /logs HTTP/1.1";
        HashMap<String, String> request = HTTPRequestParser.parse(requestLine);
        Router.getEndpoint(request, publicDirectory);

        Assert.assertEquals(true, Logger.getLog().contains(requestLine));
    }

    @Test
    public void requestToParametersReturnsParametersEndpoint() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET /parameters HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof ParametersResourceHandler);
    }

    @Test
    public void getEndpointReturnsOptionsEndPointWhenAppropriate() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET /method_options HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof OptionsResourceHandler);
    }

    @Test
    public void getEndpointReturnsRedirectResourceHandlerWhenAppropriate() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET /redirect HTTP/1.1");
        ResourceHandler resourceHandler = Router.getEndpoint(request, publicDirectory);

        assertTrue(resourceHandler instanceof RedirectResourceHandler);
    }
}
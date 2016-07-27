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
    public void routeReturnsStaticResourceActionForAStaticResource() {
        AbstractHTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof StaticResourceAction);
    }

    @Test
    public void routeReturnsCoffeeActionWhenAppropriate() {
        AbstractHTTPRequest request = new HTTPRequest("GET /coffee HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof CoffeeAction);
    }

    @Test
    public void routeReturnsNotFoundActionWhenPathIsNotRecognized() {
        AbstractHTTPRequest request = new HTTPRequest("GET /foobar HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof NotFoundAction);
    }

    @Test
    public void routeReturnsPostableActionWhenAppropriate() {
        AbstractHTTPRequest request = new HTTPRequest("POST /form HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof PostableAction);
    }

    @Test
    public void routeReturnsLogsActionWhenAppropriatePathIsRequested() {
        AbstractHTTPRequest request = new HTTPRequest("GET /logs HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof LogsAction);
    }

    @Test
    public void allRequestsAreLoggedAfterBeingRoutes() {
        String requestLine = "GET /logs HTTP/1.1";
        AbstractHTTPRequest request = new HTTPRequest(requestLine);
        Router.route(request, publicDirectory);

        Assert.assertEquals(true, Logger.getLog().contains(requestLine));
    }

    @Test
    public void requestToParametersReturnsParametersAction() {
        AbstractHTTPRequest request = new HTTPRequest("GET /parameters HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof ParametersAction);
    }

    @Test
    public void routeReturnsOptionsActionWhenAppropriate() {
        AbstractHTTPRequest request = new HTTPRequest("GET /method_options HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof OptionsAction);
    }

    @Test
    public void routeReturnsRedirectResourceHandlerWhenAppropriate() {
        AbstractHTTPRequest request = new HTTPRequest("GET /redirect HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof RedirectAction);
    }
}
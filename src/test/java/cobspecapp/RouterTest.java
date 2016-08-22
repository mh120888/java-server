package cobspecapp;

import httpmessage.HTTPRequest;
import basichttpmessage.BasicHTTPRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

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
        HTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET / HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof StaticResourceAction);
    }

    @Test
    public void routeReturnsCoffeeActionWhenAppropriate() {
        HTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /coffee HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof CoffeeAction);
    }

    @Test
    public void routeReturnsNotFoundActionWhenPathIsNotRecognized() {
        HTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /foobar HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof NotFoundAction);
    }

    @Test
    public void routeReturnsPostableActionWhenAppropriate() {
        HTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("POST /form HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof PostableAction);
    }

    @Test
    public void routeReturnsLogsActionWhenAppropriatePathIsRequested() {
        HTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /logs HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof LogsAction);
    }

    @Test
    public void allRequestsAreLoggedAfterBeingRoutes() {
        String requestLine = "GET /logs HTTP/1.1";
        HTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine(requestLine);
        Router.route(request, publicDirectory);

        Assert.assertEquals(true, Logger.getLog().contains(requestLine));
    }

    @Test
    public void requestToParametersReturnsParametersAction() {
        HTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /parameters HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof ParametersAction);
    }

    @Test
    public void routeReturnsOptionsActionWhenAppropriate() {
        HTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /method_options HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof OptionsAction);
    }

    @Test
    public void routeReturnsRedirectResourceHandlerWhenAppropriate() {
        HTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /redirect HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof RedirectAction);
    }
}
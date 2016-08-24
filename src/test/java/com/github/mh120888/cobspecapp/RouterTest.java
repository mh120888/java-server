package com.github.mh120888.cobspecapp;

import com.github.mh120888.basichttpmessage.BasicHTTPRequest;
import com.github.mh120888.httpmessage.HTTPRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class RouterTest {
    static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";
    HTTPRequest request;

    @Before
    public void setUp() throws Exception {
        Logger.clearLog();
        request = new BasicHTTPRequest();
    }

    @Test
    public void routeReturnsStaticResourceActionForAStaticResource() {
        request.setRequestLine("GET / HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof StaticResourceAction);
    }

    @Test
    public void routeReturnsCoffeeActionWhenAppropriate() {
        request.setRequestLine("GET /coffee HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof CoffeeAction);
    }

    @Test
    public void routeReturnsNotFoundActionWhenPathIsNotRecognized() {
        request.setRequestLine("GET /foobar HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof NotFoundAction);
    }

    @Test
    public void routeReturnsPostableActionWhenAppropriate() {
        request.setRequestLine("POST /form HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof PostableAction);
    }

    @Test
    public void routeReturnsLogsActionWhenAppropriatePathIsRequested() {
        request.setRequestLine("GET /logs HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof LogsAction);
    }

    @Test
    public void allRequestsAreLoggedAfterBeingRoutes() {
        String requestLine = "GET /logs HTTP/1.1";
        request.setRequestLine(requestLine);
        Router.route(request, publicDirectory);

        Assert.assertEquals(true, Logger.getLog().contains(requestLine));
    }

    @Test
    public void requestToParametersReturnsParametersAction() {
        request.setRequestLine("GET /parameters HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof ParametersAction);
    }

    @Test
    public void routeReturnsOptionsActionWhenAppropriate() {
        request.setRequestLine("GET /method_options HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof OptionsAction);
    }

    @Test
    public void routeReturnsRedirectResourceHandlerWhenAppropriate() {
        request.setRequestLine("GET /redirect HTTP/1.1");
        Action action = Router.route(request, publicDirectory);

        assertTrue(action instanceof RedirectAction);
    }
}
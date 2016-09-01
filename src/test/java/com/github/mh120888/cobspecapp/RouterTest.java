package com.github.mh120888.cobspecapp;

import com.github.mh120888.basichttpmessage.BasicHTTPRequest;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.mocks.MockFileIO;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RouterTest {
    static String publicDirectory = "something fake";
    static HTTPRequest request;
    static MockFileIO fileIO = new MockFileIO();

    @Before
    public void setUp() throws Exception {
        Logger.clearLog();
        fileIO.setFileNames(new String[0]);
        request = new BasicHTTPRequest();
    }

    @Test
    public void routeReturnsStaticResourceActionForAStaticResource() {
        String[] fileNamesInPublicDirectory = { "fakefile" };
        fileIO.setFileNames(fileNamesInPublicDirectory);
        request.setRequestLine("GET /fakefile HTTP/1.1");

        Action action = new Router(publicDirectory, fileIO).route(request);

        assertTrue(action instanceof StaticResourceAction);
    }

    @Test
    public void routeReturnsPatchStaticResourceActionForAPatchRequestForStaticResource() {
        String[] fileNamesInPublicDirectory = { "fakefile" };
        fileIO.setFileNames(fileNamesInPublicDirectory);
        request.setRequestLine("PATCH /fakefile HTTP/1.1");

        Action action = new Router(publicDirectory, fileIO).route(request);

        assertTrue(action instanceof PatchStaticResourceAction);
    }

    @Test
    public void routeReturnsCoffeeActionWhenAppropriate() {
        request.setRequestLine("GET /coffee HTTP/1.1");
        Action action = new Router(publicDirectory, fileIO).route(request);

        assertTrue(action instanceof CoffeeAction);
    }

    @Test
    public void routeReturnsTeaActionWhenAppropriate() {
        request.setRequestLine("GET /tea HTTP/1.1");
        Action action = new Router(publicDirectory, fileIO).route(request);

        assertTrue(action instanceof TeaAction);
    }

    @Test
    public void routeReturnsNotFoundActionWhenPathIsNotRecognized() {
        request.setRequestLine("GET /foobarssss HTTP/1.1");
        Action action = new Router(publicDirectory, fileIO).route(request);

        assertTrue(action instanceof NotFoundAction);
    }

    @Test
    public void routeReturnsPostableActionWhenAppropriate() {
        request.setRequestLine("POST /form HTTP/1.1");
        Action action = new Router(publicDirectory, fileIO).route(request);

        assertTrue(action instanceof FormAction);
    }

    @Test
    public void routeReturnsLogsActionWhenAppropriatePathIsRequested() {
        request.setRequestLine("GET /logs HTTP/1.1");
        Action action = new Router(publicDirectory, fileIO).route(request);

        assertTrue(action instanceof LogsAction);
    }

    @Test
    public void allRequestsAreLoggedAfterBeingRoutes() {
        String requestLine = "GET /logs HTTP/1.1";
        request.setRequestLine(requestLine);
        new Router(publicDirectory, fileIO).route(request);

        assertTrue(Logger.getLog().contains(requestLine));
    }

    @Test
    public void requestToParametersReturnsParametersAction() {
        request.setRequestLine("GET /parameters HTTP/1.1");
        Action action = new Router(publicDirectory, fileIO).route(request);

        assertTrue(action instanceof ParametersAction);
    }

    @Test
    public void routeReturnsOptionsActionWhenAppropriate() {
        request.setRequestLine("GET /method_options HTTP/1.1");
        Action action = new Router(publicDirectory, fileIO).route(request);

        assertTrue(action instanceof OptionsAction);
    }

    @Test
    public void routeReturnsRedirectResourceHandlerWhenAppropriate() {
        request.setRequestLine("GET /redirect HTTP/1.1");
        Action action = new Router(publicDirectory, fileIO).route(request);

        assertTrue(action instanceof RedirectAction);
    }
}
package cobspecapp;

import httpresponse.HTTPResponse;
import mocks.MockHTTPRequest;
import request.Request;
import response.Response;
import httprequest.HTTPRequest;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/12/16.
 */
public class LogsActionTest {
    LogsAction action;

    @Before
    public void setUp() throws Exception {
        Logger.clearLog();
        action = new LogsAction();
    }

    @Test
    public void getLogsWithoutCredentialsReturnsA401() {
        Response response = ResponseGenerator.generateResponse("GET", "/logs", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 401"));
    }

    @Test
    public void requestToLogsWithoutProperCredentialsReturnsWWWAuthenticateHeader() {
        Response response = ResponseGenerator.generateResponse("GET", "/logs", action);

        assertTrue(response.getFormattedResponse().contains("WWW-Authenticate: Basic realm=\"User Visible Realm\""));
    }

    @Test
    public void isAuthorizedReturnsTrueForCorrectCredentials() {
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/logs");
        request.addHeader("Authorization", "Basic YWRtaW46aHVudGVyMg==");

        assertTrue(action.isAuthorized(request));
    }

    @Test
    public void isAuthorizedReturnsFalseWithNoCredentials() {
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/logs");

        assertFalse(action.isAuthorized(request));
    }

    @Test
    public void isAuthorizedReturnsFalseForIncorrectCredentials() {
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/logs");
        request.addHeader("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        assertFalse(action.isAuthorized(request));
    }

    @Test
    public void requestToLogsWithCredentialsReturnsLogOfPreviousRequestsInBody() {
        Logger.addLog("GET / HTTP/1.1");
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/logs");
        request.addHeader("Authorization", "Basic YWRtaW46aHVudGVyMg==");
        Response response = action.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("GET / HTTP/1.1"));
    }
}
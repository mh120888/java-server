package com.github.mh120888.cobspecapp;

import com.github.mh120888.basichttpmessage.BasicHTTPMessageFactory;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.mocks.MockHTTPRequest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LogsActionTest {
    LogsAction action;

    @Before
    public void setUp() throws Exception {
        Logger.clearLog();
        action = new LogsAction();
    }

    @Test
    public void getLogsWithoutCredentialsReturnsA401() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/logs", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 401"));
    }

    @Test
    public void requestToLogsWithoutProperCredentialsReturnsWWWAuthenticateHeader() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/logs", action);

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
        HTTPResponse response = action.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        assertTrue(response.getFormattedResponse().contains("GET / HTTP/1.1"));
    }
}
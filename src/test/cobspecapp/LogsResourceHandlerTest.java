package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/12/16.
 */
public class LogsResourceHandlerTest {
    @Before
    public void setUp() throws Exception {
        Logger.clearLog();
    }

    @Test
    public void getLogsWithoutCredentialsReturnsA401() {
        LogsResourceHandler endpoint = new LogsResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /logs HTTP/1.1");
        String response = endpoint.getResponseData(request, new HTTPResponse());

        assertTrue(response.contains("HTTP/1.1 401"));
    }

    @Test
    public void requestToLogsWithoutProperCredentialsReturnsWWWAuthenticateHeader() {
        LogsResourceHandler endpoint = new LogsResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /logs HTTP/1.1");
        String response = endpoint.getResponseData(request, new HTTPResponse());

        assertTrue(response.contains("WWW-Authenticate: Basic realm=\"User Visible Realm\""));
    }

    @Test
    public void isAuthorizedReturnsTrueForCorrectCredentials() {
        LogsResourceHandler endpoint = new LogsResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /logs HTTP/1.1\nAuthorization: Basic YWRtaW46aHVudGVyMg==");

        assertTrue(endpoint.isAuthorized(request));
    }

    @Test
    public void isAuthorizedReturnsFalseWithNoCredentials() {
        LogsResourceHandler endpoint = new LogsResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /logs HTTP/1.1");

        assertFalse(endpoint.isAuthorized(request));
    }

    @Test
    public void isAuthorizedReturnsFalseForIncorrectCredentials() {
        LogsResourceHandler endpoint = new LogsResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /logs HTTP/1.1\nAuthorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        assertFalse(endpoint.isAuthorized(request));
    }

    @Test
    public void requestToLogsWithCredentialsReturnsLogOfPreviousRequestsInBody() {
        Logger.addLog("GET / HTTP/1.1");
        LogsResourceHandler endpoint = new LogsResourceHandler();
        AbstractHTTPRequest request = new HTTPRequest("GET /logs HTTP/1.1\nAuthorization: Basic YWRtaW46aHVudGVyMg==");
        String response = endpoint.getResponseData(request, new HTTPResponse());

        assertTrue(response.contains("GET / HTTP/1.1"));
    }
}
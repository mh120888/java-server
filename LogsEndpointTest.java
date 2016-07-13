import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/12/16.
 */
public class LogsEndpointTest {
    @Before
    public void setUp() throws Exception {
        Logger.clearLog();
    }

    @Test
    public void getLogsWithoutCredentialsReturnsA401() {
        LogsEndpoint endpoint = new LogsEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /logs HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 401 Unauthorized", response.get("responseLine"));
    }

    @Test
    public void requestToLogsWithoutProperCredentialsReturnsWWWAuthenticateHeader() {
        LogsEndpoint endpoint = new LogsEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /logs HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals(true, response.get("headers").contains("WWW-Authenticate: Basic realm=\"User Visible Realm\""));
    }

    @Test
    public void isAuthorizedReturnsTrueForCorrectCredentials() {
        LogsEndpoint endpoint = new LogsEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /logs HTTP/1.1\nAuthorization: Basic YWRtaW46aHVudGVyMg==");

        assertTrue(endpoint.isAuthorized(request));
    }

    @Test
    public void isAuthorizedReturnsFalseWithNoCredentials() {
        LogsEndpoint endpoint = new LogsEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /logs HTTP/1.1");

        assertFalse(endpoint.isAuthorized(request));
    }

    @Test
    public void isAuthorizedReturnsFalseForIncorrectCredentials() {
        LogsEndpoint endpoint = new LogsEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /logs HTTP/1.1\nAuthorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        assertFalse(endpoint.isAuthorized(request));
    }

    @Test
    public void requestToLogsWithCredentialsReturnsLogOfPreviousRequestsInBody() {
        Logger.addLog("GET / HTTP/1.1");
        LogsEndpoint endpoint = new LogsEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /logs HTTP/1.1\nAuthorization: Basic YWRtaW46aHVudGVyMg==");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("GET / HTTP/1.1", response.get("body"));
    }
}
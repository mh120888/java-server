import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/12/16.
 */
public class LogsEndpointTest {
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
}
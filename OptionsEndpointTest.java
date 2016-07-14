import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class OptionsEndpointTest {
    @Test
    public void getReponseDataReturnsCorrectResponseLineForGET() {
        OptionsEndpoint endpoint = new OptionsEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /pathdoesnotmatter HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

    @Test
    public void getReponseDataReturnsCorrectResponseLineForOPTIONS() {
        OptionsEndpoint endpoint = new OptionsEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("OPTIONS /pathdoesnotmatter HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

    @Test
    public void getResponseDataReturnsAResponseWithAnAllowHeader() {
        OptionsEndpoint endpoint = new OptionsEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /pathdoesnotmatter HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);
        HashMap<String, String> headers = HeaderParser.parse(response.get("headers"));

        assertEquals(true, headers.containsKey("Allow"));
    }

    @Test
    public void getResponseDataReturnsAResponseWithAllowHeaderWithMethodsAllowedForGivenPath() {
        OptionsEndpoint endpoint = new OptionsEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /method_options HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);
        HashMap<String, String> headers = HeaderParser.parse(response.get("headers"));
        String[] acceptedMethods = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};

        for (String method : acceptedMethods) {
            assertEquals(true, headers.get("Allow").contains(method));
        }
    }

    @Test
    public void getResponseDataReturnsAResponseWithAllowHeaderWithMethodsAllowedForADifferentPath() {
        OptionsEndpoint endpoint = new OptionsEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /method_options2 HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);
        HashMap<String, String> headers = HeaderParser.parse(response.get("headers"));
        String[] acceptedMethods = {"GET", "HEAD"};

        for (String method : acceptedMethods) {
            assertEquals(true, headers.get("Allow").contains(method));
        }
    }

    @Test
    public void getResponseDataReturnsAResponseWithAllowHeaderWithoutMethodsNotAllowedForPath() {
        OptionsEndpoint endpoint = new OptionsEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET /method_options2 HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);
        HashMap<String, String> headers = HeaderParser.parse(response.get("headers"));
        String[] disallowedMethods = {"POST", "OPTIONS", "PUT"};

        for (String method : disallowedMethods) {
            assertEquals(false, headers.get("Allow").contains(method));
        }
    }
}

//Allow: GET,OPTIONS
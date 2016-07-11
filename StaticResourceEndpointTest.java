import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class StaticResourceEndpointTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getReponseDataReturnsCorrectResponseLineForGet() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET / HTTP/1.0");
        HashMap<String, String> response = StaticResourceEndpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

    @Test
    public void getResponseDataReturns405ForInvalidMethods() {
        HashMap<String, String> request = HTTPRequestParser.parse("NOTAREALMETHOD / HTTP/1.0");
        HashMap<String, String> response = StaticResourceEndpoint.getResponseData(request);

        assertEquals("HTTP/1.1 405 Method Not Allowed", response.get("responseLine"));
    }

    @Test
    public void getIndexResponseIncludesLinksToOtherResourcesInPublic() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET / HTTP/1.0");
        HashMap<String, String> response = StaticResourceEndpoint.getResponseData(request);

        assertTrue(response.get("body").contains("<a href=\"/file1\">file1</a>"));
    }
}
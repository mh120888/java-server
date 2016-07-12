import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class RouterTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getEndpointReturnsStaticResourceEndpointForAStaticResource() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET / HTTP/1.1");
        Endpoint endpoint = Router.getEndpoint(request);

        assertTrue(endpoint instanceof StaticResourceEndpoint);
    }

    @Test
    public void getEndpointReturnsCoffeeEndpointWhenAppropriate() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET /coffee HTTP/1.1");
        Endpoint endpoint = Router.getEndpoint(request);

        assertTrue(endpoint instanceof CoffeeEndpoint);
    }

    @Test
    public void getEndpointReturnsNotFoundEndpointWhenPathIsNotRecognized() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET /foobar HTTP/1.1");
        Endpoint endpoint = Router.getEndpoint(request);

        assertTrue(endpoint instanceof NotFoundEndpoint);
    }

    @Test
    public void getEndpointReturnsPostableEndpointWhenAppropriate() {
        HashMap<String, String> request = HTTPRequestParser.parse("POST /form HTTP/1.1");
        Endpoint endpoint = Router.getEndpoint(request);

        assertTrue(endpoint instanceof PostableEndpoint);
    }

    @Test
    public void getEndpointReturnsLogsEndpointWhenAppropriatePathIsRequested() {
        HashMap<String, String> request = HTTPRequestParser.parse("GET /logs HTTP/1.1");
        Endpoint endpoint = Router.getEndpoint(request);

        assertTrue(endpoint instanceof LogsEndpoint);
    }

    @Test
    public void allRequestsAreLoggedAfterBeingRoutes() {
        String requestLine = "GET /logs HTTP/1.1";
        HashMap<String, String> request = HTTPRequestParser.parse(requestLine);
        Router.getEndpoint(request);
        List<String> log = Logger.getLog();

        assertEquals(true, log.contains(requestLine));
    }
}
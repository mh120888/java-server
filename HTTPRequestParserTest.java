import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/10/16.
 */
public class HTTPRequestParserTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void parseReturnsAGetMethodForAGetRequest() {
        HashMap<String, String> result = HTTPRequestParser.parse("GET / HTTP/1.0");

        assertEquals("GET", result.get("method"));
    }

    @Test
    public void parseReturnsAPostMethodForAPostRequest() {
        HashMap<String, String> result = HTTPRequestParser.parse("POST / HTTP/1.0");

        assertEquals("POST", result.get("method"));
    }

    @Test
    public void parseReturnsCorrectPath() {
        HashMap<String, String> result = HTTPRequestParser.parse("GET / HTTP/1.0");

        assertEquals("/", result.get("path"));
    }

    @Test
    public void parseReturnsCorrectPathEvenWhenItIsNotValid() {
        HashMap<String, String> result = HTTPRequestParser.parse("GET /not-a-real-path HTTP/1.0");

        assertEquals("/not-a-real-path", result.get("path"));
    }

    @Test
    public void parseReturnsTheCorrectHTTPVersion() {
        HashMap<String, String> result = HTTPRequestParser.parse("GET /not-a-real-path HTTP/1.0");

        assertEquals("HTTP/1.0", result.get("httpVersion"));
    }
}
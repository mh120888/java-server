import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/6/16.
 */
public class MyResponseTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void canCreateAResponseObjectGivenARequestObject() {
        MyRequest request = new MyRequest("GET / HTTP/1.0");
        assertTrue(new MyResponse(request) instanceof MyResponse);
    }

    @Test
    public void defaultResponseIs200() {
        MyRequest request = new MyRequest("GET / HTTP/1.0");
        MyResponse response = new MyResponse(request);
        assertEquals(response.getResponse(), "HTTP/1.1 200 OK\n");
    }

    @Test
    public void returns405ForInvalidMethod() {
        MyRequest request = new MyRequest("NOTAREALMETHOD / HTTP/1.0");
        MyResponse response = new MyResponse(request);
        assertEquals(response.getResponse(), "HTTP/1.1 405 Method Not Allowed\n");
    }

    @Test
    public void returns404ForAnUnknownPath() {
        MyRequest request = new MyRequest("GET /foobar HTTP/1.0");
        MyResponse response = new MyResponse(request);
        assertEquals(response.getResponse(), "HTTP/1.1 404 Not Found\n");
    }
}
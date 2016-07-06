import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/6/16.
 */
public class MyRequestTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void canCreateARequestObject() {
        assertTrue(new MyRequest("GET / HTTP/1.0") instanceof MyRequest);
    }

    @Test
    public void newRequestHasAMethodInstanceVariable() {
        MyRequest request = new MyRequest("GET / HTTP/1.0");
        assertEquals("GET", request.method);
    }

    @Test
    public void newRequestHasTheCorrectMethodInstanceVariable() {
        MyRequest request = new MyRequest("POST / HTTP/1.0");
        assertEquals(true, request.method.equals("POST"));
    }

    @Test
    public void newRequestHasAPathInstanceVariable() {
        MyRequest request = new MyRequest("GET / HTTP/1.0");
        assertEquals("/", request.path);
    }

    @Test
    public void newRequestHasTheCorrectPathInstanceVariable() {
        MyRequest request = new MyRequest("GET /somepath HTTP/1.0");
        assertEquals("/somepath", request.path);
    }
}
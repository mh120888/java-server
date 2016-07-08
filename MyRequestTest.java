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

    @Test
    public void newRequestHasAnHTTPVersionInstanceVariable() {
        MyRequest request = new MyRequest("GET / HTTP/1.0");

        assertEquals("HTTP/1.0", request.httpversion);
    }

    @Test
    public void testValidateMethodWithValidMethod() {
        MyRequest request = new MyRequest("GET / HTTP/1.0");
        boolean result = request.validateMethod();

        assertTrue("Valid method should return true", result);
    }

    @Test
    public void testValidateMethodWithAnotherValidMethod() {
        MyRequest request = new MyRequest("PUT / HTTP/1.0");
        boolean result = request.validateMethod();

        assertTrue("Valid method should return true", result);
    }

    @Test
    public void testValidateMethodWithInvalidMethod() {
        MyRequest request = new MyRequest("NOTVALID / HTTP/1.0");
        boolean result = request.validateMethod();

        assertFalse("Invalid method should return false", result);
    }

    @Test
    public void validatePathIsTrueForValidPath() {
        MyRequest request = new MyRequest("GET / HTTP/1.0");
        boolean result = request.validatePath();

        assertTrue("Valid path should return true", result);
    }

    @Test
    public void validatePathIsTrueForTea() {
        MyRequest request = new MyRequest("GET /tea HTTP/1.0");
        boolean result = request.validatePath();

        assertTrue("Tea should be a valid path", result);
    }

    @Test
    public void validatePathIsFalseForInvalidPath() {
        MyRequest request = new MyRequest("GET /foobar HTTP/1.0");
        boolean result = request.validatePath();

        assertFalse("This should be false", result);
    }

    @Test
    public void validatePathForPOSTRequestAlwaysReturnsTrue() {
        MyRequest request = new MyRequest("POST /foobar HTTP/1.0");
        boolean result = request.validatePath();

        assertTrue("This should be true", result);
    }

    @Test
    public void validatePathForPUTRequestAlwaysReturnsTrue() {
        MyRequest request = new MyRequest("PUT /foobar HTTP/1.0");
        boolean result = request.validatePath();

        assertTrue("This should be true", result);
    }

    @Test
    public void validatePathForHEADRequestReturnsFalseForInvalidPath() {
        MyRequest request = new MyRequest("HEAD /foobar HTTP/1.0");
        boolean result = request.validatePath();

        assertFalse("This should be false", result);
    }

    @Test
    public void isCoffeeReturnsTrueIfPathIsCoffee() {
        MyRequest request = new MyRequest("GET /coffee HTTP/1.0");
        boolean result = request.isCoffee();

        assertTrue(result);
    }

    @Test
    public void isCoffeeReturnsFalseIfPathIsNotCoffee() {
        MyRequest request = new MyRequest("GET /notcoffee HTTP/1.0");
        boolean result = request.isCoffee();

        assertFalse(result);
    }

    @Test
    public void requiresAuthorizationReturnsTrueForAProtectedResource() {
        MyRequest request = new MyRequest("GET /logs HTTP/1.0");
        boolean result = request.requiresAuthorization();

        assertTrue(result);
    }

    @Test
    public void requiresAuthorizationReturnsFalseForAnUnrotectedResource() {
        MyRequest request = new MyRequest("GET / HTTP/1.0");
        boolean result = request.requiresAuthorization();

        assertFalse(result);
    }
}
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

        assertTrue(response.buildResponse().contains("HTTP/1.1 200 OK"));
    }

    @Test
    public void returns405ForInvalidMethod() {
        MyRequest request = new MyRequest("NOTAREALMETHOD / HTTP/1.0");
        MyResponse response = new MyResponse(request);

        assertTrue(response.buildResponse().contains("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void returns404ForAnUnknownPath() {
        MyRequest request = new MyRequest("GET /foobar HTTP/1.0");
        MyResponse response = new MyResponse(request);

        assertTrue(response.buildResponse().contains("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void returns418ForGETCoffee() {
        MyRequest request = new MyRequest("GET /coffee HTTP/1.0");
        MyResponse response = new MyResponse(request);

        assertTrue(response.buildResponse().contains("HTTP/1.1 418"));
    }

    @Test
    public void returnsImATeapotInBodyForGETCoffee() {
        MyRequest request = new MyRequest("GET /coffee HTTP/1.0");
        MyResponse response = new MyResponse(request);

        assertTrue(response.buildResponse().contains("I'm a teapot"));
    }

    @Test
    public void returns200ForGETTea() {
        MyRequest request = new MyRequest("GET /tea HTTP/1.0");
        MyResponse response = new MyResponse(request);

        assertTrue(response.buildResponse().contains("HTTP/1.1 200 OK"));
    }

    @Test
    public void requestToLogsWithoutProperCredentialsReturns401() {
        MyRequest request = new MyRequest("GET /logs HTTP/1.0");
        MyResponse response = new MyResponse(request);

        assertTrue(response.buildResponse().contains("HTTP/1.1 401 Unauthorized"));
    }

    @Test
    public void requestToLogsWithoutProperCredentialsReturnsWWWAuthenticateHeader() {
        MyRequest request = new MyRequest("GET /logs HTTP/1.0");
        MyResponse response = new MyResponse(request);

        assertTrue(response.buildResponse().contains("WWW-Authenticate: Basic realm=\"User Visible Realm\""));
    }

    @Test
    public void getIndexResponseIncludesLinksToOtherResourcesInPublic() {
        MyRequest request = new MyRequest("GET / HTTPS/1.0");
        MyResponse response = new MyResponse(request);

        assertTrue(response.buildResponse().contains("<a href=\"/file1\">file1</a>"));
    }
}


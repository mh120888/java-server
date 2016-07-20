package httprequest;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by matthewhiggins on 7/20/16.
 */
public class HTTPRequestTest {
    @Test
    public void getMethodReturnsTheRequestMethodWhenItIsGET() {
        HTTPRequest request = new HTTPRequest("GET / HTTP/1.1\n");

        Assert.assertEquals("GET", request.getMethod());
    }

    @Test
    public void getMethodReturnsTheRequestMethodWhenItIsPOST() {
        HTTPRequest request = new HTTPRequest("POST / HTTP/1.1\n");

        Assert.assertEquals("POST", request.getMethod());
    }

    @Test
    public void getPathReturnsTheCorrectPath() {
        HTTPRequest request = new HTTPRequest("GET /somerandompath HTTP/1.1\n");

        Assert.assertEquals("/somerandompath", request.getPath());
    }

    @Test
    public void getVersionReturnsTheCorrectHTTPVersion() {
        HTTPRequest request = new HTTPRequest("GET /somerandompath HTTP/1.1\n");

        Assert.assertEquals("HTTP/1.1", request.getVersion());
    }

    @Test
    public void getAllHeadersRetrievesASingleHeader() {
        HTTPRequest request = new HTTPRequest("GET /somerandompath HTTP/1.1\nAuthorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==\n");

        Assert.assertTrue("The key \"Authorization\" was not present", request.getAllHeaders().containsKey("Authorization"));
    }

    @Test
    public void getAllHeadersRetrievesTheRightNumberOfHeaders() {
        HTTPRequest request = new HTTPRequest("GET /somerandompath HTTP/1.1\nAuthorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==\nOther: somethingelse");

        Assert.assertEquals(2, request.getAllHeaders().size());
    }

    @Test
    public void getHeaderRetrievesTheValueAssociatedWithThatHeaderIfItExists() {
        HTTPRequest request = new HTTPRequest("GET /somerandompath HTTP/1.1\nAuthorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==\n");

        Assert.assertEquals("Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==", request.getHeader("Authorization"));
    }
}
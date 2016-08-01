package httprequest;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by matthewhiggins on 7/20/16.
 */
public class HTTPRequestTest {
    @Test
    public void getMethodReturnsTheRequestMethodWhenItIsGET() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET / HTTP/1.1");

        Assert.assertEquals("GET", request.getMethod());
    }

    @Test
    public void getMethodReturnsTheRequestMethodWhenItIsPOST() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("POST / HTTP/1.1\n");

        Assert.assertEquals("POST", request.getMethod());
    }

    @Test
    public void getPathReturnsTheCorrectPath() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /somerandompath HTTP/1.1\n");

        Assert.assertEquals("/somerandompath", request.getPath());
    }

    @Test
    public void getVersionReturnsTheCorrectHTTPVersion() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /somerandompath HTTP/1.1\n");

        Assert.assertEquals("HTTP/1.1", request.getVersion());
    }

    @Test
    public void getHeaderRetrievesTheValueAssociatedWithThatHeaderIfItExists() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /somerandompath HTTP/1.1");
        request.setHeaders("Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        Assert.assertEquals("Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==", request.getHeader("Authorization"));
    }

    @Test
    public void headerExistReturnsTrueForAHeaderThatExists() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /somerandompath HTTP/1.1");
        request.setHeaders("Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        Assert.assertTrue(request.containsHeader("Authorization"));
    }

    @Test
    public void headerExistReturnsFalseForAHeaderThatDoesNotExist() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /somerandompath HTTP/1.1");
        request.setHeaders("Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        Assert.assertFalse(request.containsHeader("does not exist"));
    }

    @Test
    public void getAllParamsReturnsNoParamsIfNoneWerePassed() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /path-with-no-params HTTP/1.1\n");

        Assert.assertEquals(0, request.getAllParams().size());
    }

    @Test
    public void getAllParamsReturnsTheCorrectNumberOfParamsForARequestWithParams() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /path-with-two-params?firstParam=foobar&secondParam=alsoFoobar HTTP/1.1\n");

        Assert.assertEquals(2, request.getAllParams().size());
    }

    @Test
    public void getAllParamsContainsASpecificParamThatWasPassed() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /path-with-two-params?firstParam=foobar&secondParam=alsoFoobar HTTP/1.1\n");

        Assert.assertEquals("foobar", request.getAllParams().get("firstParam"));
    }

    @Test
    public void paramExistsReturnsTrueForAParamThatExists() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /path-with-two-params?firstParam=foobar&secondParam=alsoFoobar HTTP/1.1\n");

        Assert.assertTrue(request.paramExists("firstParam"));
    }

    @Test
    public void paramExistsReturnsFalseForAParamThatDoesNotExist() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /path-with-two-params?firstParam=foobar&secondParam=alsoFoobar HTTP/1.1\n");

        Assert.assertFalse(request.paramExists("notreal"));
    }

    @Test
    public void getParamRetrievesTheValueOfASpecificParam() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /path-with-two-params?firstParam=foobar&secondParam=alsoFoobar HTTP/1.1\n");

        Assert.assertEquals("alsoFoobar", request.getParam("secondParam"));
    }

    @Test
    public void paramValueHas20ReplacedWithASpace() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /pathwithoneparam?singleParam=two%20words HTTP/1.1\n");

        Assert.assertEquals("two words", request.getParam("singleParam"));
    }

    @Test
    public void paramValueHasPercent3CReplacedWithASpace() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /pathwithoneparam?lessThan=%3C HTTP/1.1\n");

        Assert.assertEquals("<", request.getParam("lessThan"));
    }

    @Test
    public void getBaseLocationReturnsTheCorrectDefaultLocation() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET / HTTP/1.1\n");

        Assert.assertEquals("http://localhost:5000/", request.getBaseLocation());
    }

    @Test
    public void getInitialRequestLineReturnsTheFirstLineOfTheRequest() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET / HTTP/1.1\n");

        Assert.assertEquals("GET / HTTP/1.1", request.getInitialRequestLine());
    }

    @Test
    public void getInitialRequestLineIncludesParams() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /some-path?somerandomparam=33 HTTP/1.1\n");

        Assert.assertEquals("GET /some-path?somerandomparam=33 HTTP/1.1", request.getInitialRequestLine());
    }

    @Test
    public void getHeaderParserReturnsANewHeaderParser() {
        HTTPRequest request = new HTTPRequest();
        request.setRequestLine("GET /path-does-not-matter HTTP/1.1\n");

        Assert.assertTrue(request.getHeaderParser() instanceof BasicHeaderParser);
  }
}
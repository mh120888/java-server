package com.github.mh120888.basichttpmessage;

import com.github.mh120888.httpmessage.HeaderParser;

import org.junit.Assert;
import org.junit.Test;

public class BasicHTTPRequestTest {
    @Test
    public void getMethodReturnsTheRequestMethodWhenItIsGET() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET / HTTP/1.1");

        Assert.assertEquals("GET", request.getMethod());
    }

    @Test
    public void getMethodReturnsTheRequestMethodWhenItIsPOST() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("POST / HTTP/1.1\n");

        Assert.assertEquals("POST", request.getMethod());
    }

    @Test
    public void getPathReturnsTheCorrectPath() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /somerandompath HTTP/1.1\n");

        Assert.assertEquals("/somerandompath", request.getPath());
    }

    @Test
    public void getVersionReturnsTheCorrectHTTPVersion() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /somerandompath HTTP/1.1\n");

        Assert.assertEquals("HTTP/1.1", request.getVersion());
    }

    @Test
    public void getHeaderRetrievesTheValueAssociatedWithThatHeaderIfItExists() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /somerandompath HTTP/1.1");
        request.setHeaders("Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        Assert.assertEquals("Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==", request.getHeader("Authorization"));
    }

    @Test
    public void headerExistReturnsTrueForAHeaderThatExists() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /somerandompath HTTP/1.1");
        request.setHeaders("Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        Assert.assertTrue(request.containsHeader("Authorization"));
    }

    @Test
    public void headerExistReturnsFalseForAHeaderThatDoesNotExist() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /somerandompath HTTP/1.1");
        request.setHeaders("Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");

        Assert.assertFalse(request.containsHeader("does not exist"));
    }

    @Test
    public void getAllParamsReturnsNoParamsIfNoneWerePassed() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /path-with-no-params HTTP/1.1\n");

        Assert.assertEquals(0, request.getAllParams().size());
    }

    @Test
    public void getAllParamsReturnsTheCorrectNumberOfParamsForARequestWithParams() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /path-with-two-params?firstParam=foobar&secondParam=alsoFoobar HTTP/1.1\n");

        Assert.assertEquals(2, request.getAllParams().size());
    }

    @Test
    public void getAllParamsContainsASpecificParamThatWasPassed() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /path-with-two-params?firstParam=foobar&secondParam=alsoFoobar HTTP/1.1\n");

        Assert.assertEquals("foobar", request.getAllParams().get("firstParam"));
    }

    @Test
    public void paramExistsReturnsTrueForAParamThatExists() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /path-with-two-params?firstParam=foobar&secondParam=alsoFoobar HTTP/1.1\n");

        Assert.assertTrue(request.paramExists("firstParam"));
    }

    @Test
    public void paramExistsReturnsFalseForAParamThatDoesNotExist() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /path-with-two-params?firstParam=foobar&secondParam=alsoFoobar HTTP/1.1\n");

        Assert.assertFalse(request.paramExists("notreal"));
    }

    @Test
    public void getParamRetrievesTheValueOfASpecificParam() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /path-with-two-params?firstParam=foobar&secondParam=alsoFoobar HTTP/1.1\n");

        Assert.assertEquals("alsoFoobar", request.getParam("secondParam"));
    }

    @Test
    public void paramValueHas20ReplacedWithASpace() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /pathwithoneparam?singleParam=two%20words HTTP/1.1\n");

        Assert.assertEquals("two words", request.getParam("singleParam"));
    }

    @Test
    public void paramValueHasPercent3CReplacedWithASpace() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /pathwithoneparam?lessThan=%3C HTTP/1.1\n");

        Assert.assertEquals("<", request.getParam("lessThan"));
    }

    @Test
    public void getBaseLocationReturnsTheCorrectDefaultLocation() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET / HTTP/1.1\n");

        Assert.assertEquals("http://localhost:5000/", request.getBaseLocation());
    }

    @Test
    public void getInitialRequestLineReturnsTheFirstLineOfTheRequest() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET / HTTP/1.1\n");

        Assert.assertEquals("GET / HTTP/1.1", request.getInitialRequestLine());
    }

    @Test
    public void getInitialRequestLineIncludesParams() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /some-path?somerandomparam=33 HTTP/1.1\n");

        Assert.assertEquals("GET /some-path?somerandomparam=33 HTTP/1.1", request.getInitialRequestLine());
    }

    @Test
    public void getHeaderParserReturnsANewHeaderParser() {
        BasicHTTPRequest request = new BasicHTTPRequest();
        request.setRequestLine("GET /path-does-not-matter HTTP/1.1\n");

        Assert.assertTrue(request.getHeaderParser() instanceof HeaderParser);
  }
}
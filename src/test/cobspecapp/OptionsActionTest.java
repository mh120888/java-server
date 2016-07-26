package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class OptionsActionTest {
    @Test
    public void getReponseDataReturnsCorrectResponseLineForGET() {
        OptionsAction endpoint = new OptionsAction();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        Assert.assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getReponseDataReturnsCorrectResponseLineForOPTIONS() {
        OptionsAction endpoint = new OptionsAction();
        AbstractHTTPRequest request = new HTTPRequest("OPTIONS /pathdoesnotmatter HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        Assert.assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturnsAResponseWithAnAllowHeader() {
        OptionsAction endpoint = new OptionsAction();
        AbstractHTTPRequest request = new HTTPRequest("GET /pathdoesnotmatter HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        Assert.assertTrue(response.getFormattedResponse().contains("Allow: "));
    }

    @Test
    public void getResponseReturnsAResponseWithAllowHeaderWithMethodsAllowedForGivenPath() {
        OptionsAction endpoint = new OptionsAction();
        AbstractHTTPRequest request = new HTTPRequest("GET /method_options HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());
        String[] acceptedMethods = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};

        for (String method : acceptedMethods) {
            Assert.assertTrue(response.getFormattedResponse().contains(method));
        }
    }

    @Test
    public void getResponseReturnsAResponseWithAllowHeaderWithMethodsAllowedForADifferentPath() {
        OptionsAction endpoint = new OptionsAction();
        AbstractHTTPRequest request = new HTTPRequest("GET /method_options2 HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());
        String[] acceptedMethods = {"GET", "OPTIONS"};

        for (String method : acceptedMethods) {
            Assert.assertTrue(response.getFormattedResponse().contains(method));
        }
    }

    @Test
    public void getResponseReturnsAResponseWithAllowHeaderWithoutMethodsNotAllowedForPath() {
        OptionsAction endpoint = new OptionsAction();
        AbstractHTTPRequest request = new HTTPRequest("GET /method_options2 HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());
        String[] disallowedMethods = {"POST", "HEAD", "PUT"};

        for (String method : disallowedMethods) {
            Assert.assertFalse(response.getFormattedResponse().contains(method));
        }

    }
}

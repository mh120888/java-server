package cobspecapp;

import abstracthttpresponse.AbstractHTTPResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class OptionsActionTest {
    OptionsAction action;

    @Before
    public void setup() {
        action = new OptionsAction();
    }

    @Test
    public void getReponseDataReturnsCorrectResponseLineForGET() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /pathdoesnotmatter HTTP/1.1", action);

        Assert.assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getReponseDataReturnsCorrectResponseLineForOPTIONS() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("OPTIONS /pathdoesnotmatter HTTP/1.1", action);

        Assert.assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturnsAResponseWithAnAllowHeader() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /pathdoesnotmatter HTTP/1.1", action);

        Assert.assertTrue(response.getFormattedResponse().contains("Allow: "));
    }

    @Test
    public void getResponseReturnsAResponseWithAllowHeaderWithMethodsAllowedForGivenPath() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /method_options HTTP/1.1", action);

        String[] acceptedMethods = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};
        for (String method : acceptedMethods) {
            Assert.assertTrue(response.getFormattedResponse().contains(method));
        }
    }

    @Test
    public void getResponseReturnsAResponseWithAllowHeaderWithMethodsAllowedForADifferentPath() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /method_options2 HTTP/1.1", action);

        String[] acceptedMethods = {"GET", "OPTIONS"};

        for (String method : acceptedMethods) {
            Assert.assertTrue(response.getFormattedResponse().contains(method));
        }
    }

    @Test
    public void getResponseReturnsAResponseWithAllowHeaderWithoutMethodsNotAllowedForPath() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /method_options2 HTTP/1.1", action);

        String[] disallowedMethods = {"POST", "HEAD", "PUT"};

        for (String method : disallowedMethods) {
            Assert.assertFalse(response.getFormattedResponse().contains(method));
        }

    }
}

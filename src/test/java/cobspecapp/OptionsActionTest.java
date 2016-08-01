package cobspecapp;

import response.Response;
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
        Response response = ResponseGenerator.generateResponse("GET", "/pathdoesnotmatter", action);

        Assert.assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getReponseDataReturnsCorrectResponseLineForOPTIONS() {
        Response response = ResponseGenerator.generateResponse("OPTIONS", "/pathdoesnotmatter", action);

        Assert.assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturnsAResponseWithAnAllowHeader() {
        Response response = ResponseGenerator.generateResponse("GET", "/pathdoesnotmatter", action);

        Assert.assertTrue(response.getFormattedResponse().contains("Allow: "));
    }

    @Test
    public void getResponseReturnsAResponseWithAllowHeaderWithMethodsAllowedForGivenPath() {
        Response response = ResponseGenerator.generateResponse("GET", "/method_options", action);

        String[] acceptedMethods = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};
        for (String method : acceptedMethods) {
            Assert.assertTrue(response.getFormattedResponse().contains(method));
        }
    }

    @Test
    public void getResponseReturnsAResponseWithAllowHeaderWithMethodsAllowedForADifferentPath() {
        Response response = ResponseGenerator.generateResponse("GET", "/method_options2", action);

        String[] acceptedMethods = {"GET", "OPTIONS"};

        for (String method : acceptedMethods) {
            Assert.assertTrue(response.getFormattedResponse().contains(method));
        }
    }

    @Test
    public void getResponseReturnsAResponseWithAllowHeaderWithoutMethodsNotAllowedForPath() {
        Response response = ResponseGenerator.generateResponse("GET", "/method_options2", action);

        String[] disallowedMethods = {"POST", "HEAD", "PUT"};

        for (String method : disallowedMethods) {
            Assert.assertFalse(response.getFormattedResponse().contains(method));
        }

    }
}

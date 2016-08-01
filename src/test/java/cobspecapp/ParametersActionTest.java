package cobspecapp;

import httpresponse.HTTPResponse;
import mocks.MockHTTPRequest;
import response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class ParametersActionTest {
    ParametersAction action;

    @Before
    public void setup() {
        action = new ParametersAction();
    }

    @Test
    public void GETRequestReturns200() {
        Response response = ResponseGenerator.generateResponse("GET", "/parameters", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void POSTRequestReturns405() {
        Response response = ResponseGenerator.generateResponse("POST", "/parameters", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 405"));
    }

    @Test
    public void GETRequestWithParametersIncludesParametersInBody() {
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/parameters");
        request.addParam("someParam", "newValue");
        Response response = action.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("someParam = newValue"));
    }
}
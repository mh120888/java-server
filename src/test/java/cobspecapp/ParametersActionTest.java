package cobspecapp;

import basichttpmessage.BasicHTTPMessageFactory;
import mocks.MockHTTPRequest;
import httpmessage.HTTPResponse;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ParametersActionTest {
    ParametersAction action;

    @Before
    public void setup() {
        action = new ParametersAction();
    }

    @Test
    public void GETRequestReturns200() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/parameters", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void POSTRequestReturns405() {
        HTTPResponse response = ResponseGenerator.generateResponse("POST", "/parameters", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 405"));
    }

    @Test
    public void GETRequestWithParametersIncludesParametersInBody() {
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/parameters");
        request.addParam("someParam", "newValue");
        HTTPResponse response = action.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        assertTrue(response.getFormattedResponse().contains("someParam = newValue"));
    }
}
package cobspecapp;

import abstracthttpresponse.AbstractHTTPResponse;
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
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /parameters HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void POSTRequestReturns405() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("POST /parameters HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 405"));
    }

    @Test
    public void GETRequestWithParametersIncludesParametersInBody() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /parameters?someParam=newValue HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("someParam = newValue"));
    }
}
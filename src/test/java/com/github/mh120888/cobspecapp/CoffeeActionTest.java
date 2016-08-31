package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class CoffeeActionTest {
    CoffeeAction action;

    @Before
    public void setUp() throws Exception {
        action = new CoffeeAction();
    }

    @Test
    public void returns418ForGETCoffee() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/coffee", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.IM_A_TEAPOT)));
    }

    @Test
    public void bodyContainsCorrectStringIfPathIsCoffee() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/coffee", action);

        assertTrue(response.getFormattedResponse().contains("I'm a teapot"));
    }
}
package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TeaActionTest {
    TeaAction action;

    @Before
    public void setUp() throws Exception {
        action = new TeaAction();
    }

    @Test
    public void returns200ForGETTea() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/tea", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.OK)));
    }
}
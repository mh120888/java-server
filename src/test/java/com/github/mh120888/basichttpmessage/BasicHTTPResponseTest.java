package com.github.mh120888.basichttpmessage;

import com.github.mh120888.httpmessage.HTTPStatus;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BasicHTTPResponseTest {
    @Test
    public void setStatusChangesStatusMember() {
        BasicHTTPResponse response = new BasicHTTPMessageFactory().getNewResponse();

        response.setStatus(HTTPStatus.OK);

        Assert.assertEquals(HTTPStatus.OK, response.status);
    }

    @Test
    public void setHTTPVersionChangesVersionMember() {
        BasicHTTPResponse response = new BasicHTTPMessageFactory().getNewResponse();

        response.setHTTPVersion("HTTP/1.1");

        Assert.assertEquals("HTTP/1.1", response.version);
    }

    @Test
    public void setBodyUpdatesTheBodyMemberToTheProvidedString() {
        BasicHTTPResponse response = new BasicHTTPMessageFactory().getNewResponse();

        response.setBody("random body content".getBytes());

        Assert.assertEquals("random body content", new String(response.body));
    }

    @Test
    public void setBodyOverwritesPreviousBodyContents() {
        BasicHTTPResponse response = new BasicHTTPMessageFactory().getNewResponse();
        response.setBody("random body content".getBytes());

        response.setBody("new body content".getBytes());

        Assert.assertEquals("new body content", new String(response.body));
    }

    @Test
    public void addHeaderAddsANewHeaderToTheResponse() {
        BasicHTTPResponse response = new BasicHTTPMessageFactory().getNewResponse();

        response.addHeader("some name", "some value");

        Assert.assertEquals(response.headers.get("some name"), "some value");
    }

    @Test
    public void getFormattedResponseReturnsAProperlyFormattedVersionOfTheResponse() {
        BasicHTTPResponse response = new BasicHTTPMessageFactory().getNewResponse();
        response.setStatus(HTTPStatus.OK);
        response.setHTTPVersion("HTTP/1.1");
        response.setBody("Some content".getBytes());
        response.addHeader("One header", "one value");

        Assert.assertEquals("HTTP/1.1 200 OK\nOne header: one value\n\nSome content", response.getFormattedResponse());
    }

    @Test
    public void getFormattedHeadersReturnsAStringOfAllHeaderProperlyFormatted() {
        BasicHTTPResponse response = new BasicHTTPMessageFactory().getNewResponse();
        response.addHeader("One header", "one value");
        response.addHeader("second", "something random");

        Assert.assertEquals("One header: one value\nsecond: something random\n", response.getFormattedHeaders());
    }

    @Test
    public void getStatusTextReturnsCorrectTextForAllStatusCodes() {
        BasicHTTPResponse response = new BasicHTTPMessageFactory().getNewResponse();
        ArrayList<Integer> statusCodes = getAllStatusCodes();
        for (Integer status : statusCodes) {
            response.setStatus(status.intValue());
            String expected = HTTPStatus.getStatusText(response.status);
            Assert.assertEquals(expected, response.getStatusText());
        }
    }

    public ArrayList<Integer> getAllStatusCodes() {
        ArrayList<Integer> statusCodes = new ArrayList<>();
        Field[] declaredFields = HTTPStatus.class.getDeclaredFields();
        for (Field field : declaredFields) {
            try {
                statusCodes.add(field.getInt(null));
            } catch (IllegalAccessException e) {
                System.err.println(e.getStackTrace());
            }
        }
        return statusCodes;
    }

    @Test
    public void getStatusTextReturnsAnEmptyStringForAnUnknownStatusCode() {
        BasicHTTPResponse response = new BasicHTTPMessageFactory().getNewResponse();
        response.setStatus(0);

        Assert.assertEquals("", response.getStatusText());
    }
}
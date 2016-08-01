package httpresponse;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by matthewhiggins on 7/21/16.
 */
public class HTTPResponseTest {
    @Test
    public void setStatusChangesStatusMember() {
        HTTPResponse response = new HTTPResponse();

        response.setStatus(200);

        Assert.assertEquals(200, response.status);
    }

    @Test
    public void setHTTPVersionChangesVersionMember() {
        HTTPResponse response = new HTTPResponse();

        response.setHTTPVersion("HTTP/1.1");

        Assert.assertEquals("HTTP/1.1", response.version);
    }

    @Test
    public void setBodyUpdatesTheBodyMemberToTheProvidedString() {
        HTTPResponse response = new HTTPResponse();

        response.setBody("random body content".getBytes());

        Assert.assertEquals("random body content", new String(response.body));
    }

    @Test
    public void setBodyOverwritesPreviousBodyContents() {
        HTTPResponse response = new HTTPResponse();
        response.setBody("random body content".getBytes());

        response.setBody("new body content".getBytes());

        Assert.assertEquals("new body content", new String(response.body));
    }

    @Test
    public void addHeaderAddsANewHeaderToTheResponse() {
        HTTPResponse response = new HTTPResponse();

        response.addHeader("some name", "some value");

        Assert.assertEquals(response.headers.get("some name"), "some value");
    }

    @Test
    public void getFormattedResponseReturnsAProperlyFormattedVersionOfTheResponse() {
        HTTPResponse response = new HTTPResponse();
        response.setStatus(200);
        response.setHTTPVersion("HTTP/1.1");
        response.setBody("Some content".getBytes());
        response.addHeader("One header", "one value");

        Assert.assertEquals("HTTP/1.1 200 OK\nOne header: one value\n\nSome content", response.getFormattedResponse());
    }

    @Test
    public void getFormattedHeadersReturnsAStringOfAllHeaderProperlyFormatted() {
        HTTPResponse response = new HTTPResponse();
        response.addHeader("One header", "one value");
        response.addHeader("second", "something random");

        Assert.assertEquals("One header: one value\nsecond: something random\n", response.getFormattedHeaders());
    }

    @Test
    public void getStatusTextReturnsOKFor200() {
        HTTPResponse response = new HTTPResponse();
        response.setStatus(200);

        Assert.assertEquals("OK", response.getStatusText());
    }

    @Test
    public void getStatusTextReturnsNotFoundFor404() {
        HTTPResponse response = new HTTPResponse();
        response.setStatus(404);

        Assert.assertEquals("Not Found", response.getStatusText());
    }

    @Test
    public void getStatusTextReturnsMethodNotAllowedFor405() {
        HTTPResponse response = new HTTPResponse();
        response.setStatus(405);

        Assert.assertEquals("Method Not Allowed", response.getStatusText());
    }

    @Test
    public void getStatusTextReturnsAnEmptyStringForAnUnknownStatusCode() {
        HTTPResponse response = new HTTPResponse();
        response.setStatus(0);

        Assert.assertEquals("", response.getStatusText());
    }
}
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

        response.setBody("random body content");

        Assert.assertEquals("random body content", response.body);
    }

    @Test
    public void setBodyOverwritesPreviousBodyContents() {
        HTTPResponse response = new HTTPResponse();
        response.setBody("random body content");

        response.setBody("new body content");

        Assert.assertEquals("new body content", response.body);
    }

    @Test
    public void addToBodyAddsNewContentToBody() {
        HTTPResponse response = new HTTPResponse();
        response.setBody("random body content");

        response.addToBody("more stuff");

        Assert.assertTrue(response.body.contains("more stuff"));
    }

    @Test
    public void addToBodyDoesNotOverwritePreviousBodyContents() {
        HTTPResponse response = new HTTPResponse();
        response.setBody("random body content");

        response.addToBody("more stuff");

        Assert.assertTrue(response.body.contains("random body content"));
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
        response.setBody("Some content");
        response.addHeader("One header", "one value");

        Assert.assertEquals("HTTP/1.1 200\nOne header: one value\n\nSome content", response.getFormattedResponse());
    }

    @Test
    public void getFormattedHeadersReturnsAStringOfAllHeaderProperlyFormatted() {
        HTTPResponse response = new HTTPResponse();
        response.addHeader("One header", "one value");
        response.addHeader("second", "something random");

        Assert.assertEquals("One header: one value\nsecond: something random\n", response.getFormattedHeaders());
    }
}
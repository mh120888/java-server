package server;

import abstracthttprequest.AbstractHTTPRequest;
import app.Application;
import mocks.MockApplication;
import mocks.MockHTTPRequest;
import mocks.MockOutputStream;
import mocks.MockPrintStream;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;


/**
 * Created by matthewhiggins on 7/27/16.
 */
public class ConnectionHandlerTest {
    @Test
    public void readInInputTrimsEachLineAndEndsItWithANewlineCharacter() throws Exception {
        BufferedReader in = new BufferedReader(new StringReader("Some string with whitespace at the end    "));
        String result = ConnectionHandler.readInFirstLineAndHeaders(in);

        Assert.assertEquals("Some string with whitespace at the end\n", result);
    }

    @Test
    public void readInInputWorksProperlyForMultiLineInput() throws Exception {
        BufferedReader in = new BufferedReader(new StringReader("Line one\nLine two   "));
        String result = ConnectionHandler.readInFirstLineAndHeaders(in);

        Assert.assertEquals("Line one\nLine two\n", result);
    }

    @Test
    public void generateOutputWritesToOutputStream() throws Exception {
        AbstractHTTPRequest request = new MockHTTPRequest();
        MockPrintStream out = new MockPrintStream(new MockOutputStream());
        Application app = new MockApplication("Random response");
        ConnectionHandler.generateOutput(request, out, app);

        Assert.assertTrue(out.lastMessage.contains("Random response"));
    }

    @Test
    public void readInBodyReadsTheSpecifiedNumberOfOctets() throws Exception {
        BufferedReader reader = new BufferedReader(new StringReader("I am 18 bytes long"));
        Assert.assertEquals("I am 18 by", ConnectionHandler.readInBody(reader, 10));
    }
}
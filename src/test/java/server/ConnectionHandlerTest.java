package server;

import request.Request;
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
    public void buildHttpRequestReturnsARequestObjectWithAProperRequestLine() {
        BufferedReader in = new BufferedReader(new StringReader("GET / HTTP/1.1\n"));
        Request request = ConnectionHandler.buildHttpRequest(in);

        Assert.assertEquals("GET / HTTP/1.1", request.getInitialRequestLine());
    }

    @Test
    public void buildHttpRequestReturnsARequestObjectWithAnEmptyBodyWhenNoneIsProvided() {
        BufferedReader in = new BufferedReader(new StringReader("GET / HTTP/1.1\n"));
        Request request = ConnectionHandler.buildHttpRequest(in);

        Assert.assertTrue(request.getBody().isEmpty());
    }

    @Test
    public void buildHttpRequestReturnsARequestObjectWithCorrectHeaders() {
        BufferedReader in = new BufferedReader(new StringReader("GET / HTTP/1.1\nTest header: 29393939\nAnother test: 40223840"));
        Request request = ConnectionHandler.buildHttpRequest(in);

        Assert.assertTrue(request.containsHeader("Another test"));
        Assert.assertTrue(request.containsHeader("Test header"));
    }

    @Test
    public void buildHttpRequestReturnsARequestObjectWithBody() {
        BufferedReader in = new BufferedReader(new StringReader("GET / HTTP/1.1\r\n" +
                                                                "Content-Length: 12\n" +
                                                                "\n" +
                                                                "body content"));
        Request request = ConnectionHandler.buildHttpRequest(in);

        Assert.assertEquals("body content", request.getBody());
    }

    @Test
    public void readInFirstLineReturnsLineWithoutTrailingWhitespace() {
        BufferedReader in = new BufferedReader(new StringReader("Some string with whitespace at the end    "));
        String result = ConnectionHandler.readInFirstLine(in);

        Assert.assertEquals("Some string with whitespace at the end", result);

    }

    @Test
    public void readInFirstLineReturnsOnlyASingleLine() {
        BufferedReader in = new BufferedReader(new StringReader("Line one\nLine two"));
        String result = ConnectionHandler.readInFirstLine(in);

        Assert.assertEquals("Line one", result);
    }

    @Test
    public void readInFirstLineReturnEmptyStringIfGivenEmptyInput() {
        BufferedReader in = new BufferedReader(new StringReader("  "));
        String result = ConnectionHandler.readInFirstLine(in);

        Assert.assertEquals("", result);
    }

    @Test
    public void readInHeadersTrimsEachLineAndEndsItWithANewlineCharacter() throws Exception {
        BufferedReader in = new BufferedReader(new StringReader("Some string with whitespace at the end    "));
        String result = ConnectionHandler.readInHeaders(in);

        Assert.assertEquals("Some string with whitespace at the end\n", result);
    }

    @Test
    public void readInHeadersWorksProperlyForMultiLineInput() throws Exception {
        BufferedReader in = new BufferedReader(new StringReader("Line one\nLine two   "));
        String result = ConnectionHandler.readInHeaders(in);

        Assert.assertEquals("Line one\nLine two\n", result);
    }

    @Test
    public void generateOutputWritesToOutputStream() throws Exception {
        Request request = new MockHTTPRequest();
        MockPrintStream out = new MockPrintStream(new MockOutputStream());
        Application app = new MockApplication("Random response");
        ConnectionHandler.generateOutput(request, out, app);

        Assert.assertTrue(out.lastMessage.contains("Random response"));
    }

    @Test
    public void readInBodyReadsTheSpecifiedNumberOfBytes() throws Exception {
        BufferedReader reader = new BufferedReader(new StringReader("I am 18 bytes long"));
        Assert.assertEquals("I am 18 by", ConnectionHandler.readInBody(reader, 10));
    }
}
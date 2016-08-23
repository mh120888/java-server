package com.github.mh120888.server;

import com.github.mh120888.app.Application;
import com.github.mh120888.basichttpmessage.BasicHTTPMessageFactory;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.mocks.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandlerTest {
    ConnectionHandler connectionHandler;
    ServerSocket dummyServerSocket;
    Socket dummySocket;

    @Before
    public void setUp() throws IOException {
        dummyServerSocket = new ServerSocket(3000);
        dummySocket = new Socket("localhost", 3000);
        connectionHandler = new ConnectionHandler(dummySocket, new MockApplication("Random response"), new BasicHTTPMessageFactory());
    }

    @After
    public void tearDown() throws IOException {
        dummySocket.close();
        dummyServerSocket.close();
    }

    @Test
    public void buildHttpRequestReturnsARequestObjectWithAProperRequestLine() throws IOException {
        BufferedReader in = new BufferedReader(new StringReader("GET / HTTP/1.1\n"));
        HTTPRequest request = connectionHandler.buildHttpRequest(in);

        Assert.assertEquals("GET / HTTP/1.1", request.getInitialRequestLine());
    }

    @Test
    public void buildHttpRequestReturnsARequestObjectWithAnEmptyBodyWhenNoneIsProvided() throws IOException {
        BufferedReader in = new BufferedReader(new StringReader("GET / HTTP/1.1\n"));
        HTTPRequest request = connectionHandler.buildHttpRequest(in);

        Assert.assertTrue(request.getBody().isEmpty());
    }

    @Test
    public void buildHttpRequestReturnsARequestObjectWithCorrectHeaders() throws IOException {
        BufferedReader in = new BufferedReader(new StringReader("GET / HTTP/1.1\nTest header: 29393939\nAnother test: 40223840"));
        HTTPRequest request = connectionHandler.buildHttpRequest(in);

        Assert.assertTrue(request.containsHeader("Another test"));
        Assert.assertTrue(request.containsHeader("Test header"));
    }

    @Test
    public void buildHttpRequestReturnsARequestObjectWithBody() throws IOException {
        BufferedReader in = new BufferedReader(new StringReader("GET / HTTP/1.1\r\n" +
                                                                "Content-Length: 12\n" +
                                                                "\n" +
                                                                "body content"));
        HTTPRequest request = connectionHandler.buildHttpRequest(in);

        Assert.assertEquals("body content", request.getBody());
    }

    @Test
    public void readInFirstLineReturnsLineWithoutTrailingWhitespace() throws IOException {
        BufferedReader in = new BufferedReader(new StringReader("Some string with whitespace at the end    "));
        String result = connectionHandler.readInFirstLine(in);

        Assert.assertEquals("Some string with whitespace at the end", result);

    }

    @Test
    public void readInFirstLineReturnsOnlyASingleLine() throws IOException {
        BufferedReader in = new BufferedReader(new StringReader("Line one\nLine two"));
        String result = connectionHandler.readInFirstLine(in);

        Assert.assertEquals("Line one", result);
    }

    @Test
    public void readInFirstLineReturnEmptyStringIfGivenEmptyInput() throws IOException {
        BufferedReader in = new BufferedReader(new StringReader("  "));
        String result = connectionHandler.readInFirstLine(in);

        Assert.assertEquals("", result);
    }

    @Test
    public void readInHeadersTrimsEachLineAndEndsItWithANewlineCharacter() throws Exception {
        BufferedReader in = new BufferedReader(new StringReader("Some string with whitespace at the end    "));
        String result = connectionHandler.readInHeaders(in);

        Assert.assertEquals("Some string with whitespace at the end\n", result);
    }

    @Test
    public void readInHeadersWorksProperlyForMultiLineInput() throws Exception {
        BufferedReader in = new BufferedReader(new StringReader("Line one\nLine two   "));
        String result = connectionHandler.readInHeaders(in);

        Assert.assertEquals("Line one\nLine two\n", result);
    }

    @Test
    public void generateOutputWritesToOutputStream() throws Exception {
        HTTPRequest request = new MockHTTPRequest();
        MockPrintStream out = new MockPrintStream(new MockOutputStream());
        Application app = new MockApplication("Random response");
        connectionHandler.generateOutput(request, out);

        Assert.assertTrue(out.lastMessage.contains("Random response"));
    }

    @Test
    public void readInBodyReadsTheSpecifiedNumberOfBytes() throws Exception {
        BufferedReader reader = new BufferedReader(new StringReader("I am 18 bytes long"));
        Assert.assertEquals("I am 18 by", connectionHandler.readInBody(reader, 10));
    }
}
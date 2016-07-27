package server;

import abstracthttprequest.AbstractHTTPRequest;
import app.Application;
import mocks.MockApplication;
import mocks.MockHTTPRequest;
import mocks.MockOutputStream;
import mocks.MockPrintStream;
import org.junit.Assert;
import org.junit.Test;
import java.io.*;

/**
 * Created by matthewhiggins on 7/5/16.
 */
public class MyServerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @org.junit.Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @org.junit.After
    public void tearDown() throws Exception {
        System.setOut(null);
        System.setErr(null);
    }
}
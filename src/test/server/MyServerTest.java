package server;

import app.Application;
import mocks.MockApplication;
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

    @Test
    public void readInInputTrimsEachLineAndEndsItWithANewlineCharacter() throws Exception {
        BufferedReader in = new BufferedReader(new StringReader("Some string with whitespace at the end    "));
        String result = MyServer.readInInput(in);

        Assert.assertEquals("Some string with whitespace at the end\n", result);
    }

    @Test
    public void readInInputWorksProperlyForMultiLineInput() throws Exception {
        BufferedReader in = new BufferedReader(new StringReader("Line one\nLine two   "));
        String result = MyServer.readInInput(in);

        Assert.assertEquals("Line one\nLine two\n", result);
    }

    @Test
    public void readInInputIgnoresAnythingAfterAnEmptyLine() throws Exception {
        BufferedReader in = new BufferedReader(new StringReader("     \nLine two\nLine three   "));
        String result = MyServer.readInInput(in);

        Assert.assertEquals("", result);
    }

    @Test
    public void generateOutputWritesToOutputStream() throws Exception {
        String input = "Something random, doesn't matter";
        MockPrintStream out = new MockPrintStream(new MockOutputStream());
        Application app = new MockApplication("Random response");
        MyServer.generateOutput(input, out, app);

        Assert.assertEquals("Random response", out.lastMessage);
    }
}
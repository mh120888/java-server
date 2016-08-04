package server;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;

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
        MyServer.myPort = 3000;
        MyServer.publicDirectory = "~/";
    }

    @org.junit.After
    public void tearDown() throws Exception {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void getPortNumberReturnsDefaultPortIfNoPortSpecifiedInOptions() {
        HashMap<String, String> options = new HashMap<>();

        int port = MyServer.getPortNumber(options);
        Assert.assertEquals(3000, port);
    }

    @Test
    public void getPortNumberWillReturnNewPortNumberIfItsAnInt() {
        HashMap<String, String> options = new HashMap<>();
        options.put("-p", "5001");

        int port = MyServer.getPortNumber(options);
        Assert.assertEquals(5001, port);
    }

    @Test
    public void getPortNumberReturnsDefaultPortIfPortInOptionsIsNotAnInt() {
        HashMap<String, String> options = new HashMap<>();
        options.put("-p", "not an int");

        int port = MyServer.getPortNumber(options);
        Assert.assertEquals(3000, port);
    }

    @Test
    public void getPublicDirectoryReturnsDefaultIfNoneIsSpecifiedInOptions() {
        HashMap<String, String> options = new HashMap<>();

        String directory = MyServer.setPublicDirectory(options);
        Assert.assertEquals("~/", directory);
    }

    @Test
    public void getPublicDirectoryReturnsDefaultIfOptionsSpecifyDirectoryThatDoesNotExist() {
        HashMap<String, String> options = new HashMap<>();
        options.put("-d", "this is not a real directory");

        String directory = MyServer.setPublicDirectory(options);
        Assert.assertEquals("~/", directory);
    }

    @Test
    public void getPublicDirectoryReturnsDirectoryFromOptionsIfItsValid() {
        HashMap<String, String> options = new HashMap<>();
        options.put("-d", "/Users/");

        String directory = MyServer.setPublicDirectory(options);
        Assert.assertEquals("/Users/", directory);
    }
}
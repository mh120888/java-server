package com.github.mh120888.server;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import java.io.*;
import java.security.Permission;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class MyServerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private SecurityManager securityManager;
    private final int DEFAULTPORT = 3000;
    private final String DEFAULTDIRECTORY = "/default/";

    @org.junit.Before
    public void setUp() throws Exception {
        MyServer.myPort = DEFAULTPORT;
        MyServer.publicDirectory = DEFAULTDIRECTORY;

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        securityManager = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
            }

            @Override
            public void checkPermission(Permission perm, Object context) {
            }

            @Override
            public void checkExit(int status) {
                String message = "System exit requested with error " + status;
                throw new SecurityException(message);
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        System.setSecurityManager(securityManager);
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void getPortNumberReturnsDefaultPortIfNoPortSpecifiedInOptions() {
        HashMap<String, String> options = new HashMap<>();

        int port = MyServer.getPortNumber(options);

        Assert.assertEquals(DEFAULTPORT, port);
    }

    @Test
    public void getPortNumberWillReturnNewPortNumberIfItIsAnInt() {
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

        Assert.assertEquals(DEFAULTPORT, port);
    }

    @Test
    public void getPublicDirectoryReturnsDirectoryFromOptionsIfItIsAValidDirectory() {
        HashMap<String, String> options = new HashMap<>();
        options.put("-d", "/Users/");

        String directory = MyServer.getPublicDirectory(options);

        Assert.assertEquals("/Users/", directory);
    }

    @Test
    public void verifyAndSetOptionsWithValidPortButWithoutPublicDirectoryOptionExitsWithStatus64() throws Exception {
        String[] args = {"-p", "5000"};
        String exceptionMessage = "no error";

        try {
            MyServer.verifyAndSetOptions(args);
        } catch (SecurityException e) {
            exceptionMessage = e.getMessage();
        } finally {
            assertEquals("System exit requested with error 64", exceptionMessage);
        }
    }

    @Test
    public void verifyAndSetOptionsDoesNotSetPortOrDirectoryIfDirectoryIsPresentButInvalid() throws Exception {
        String[] args = {"-p", "5000", "-d", "not a valid directory"};

        try {
            MyServer.verifyAndSetOptions(args);
        } catch (SecurityException e) {

        } finally {
            assertEquals(DEFAULTDIRECTORY, MyServer.publicDirectory);
            assertEquals(DEFAULTPORT, MyServer.myPort);
        }
    }

    @Test
    public void verifyAndSetOptionsSetsPortAndDirectoryWhenBothOptionsArePresentAndValid() throws Exception {
        String[] args = {"-p", "5000", "-d", "/Users/"};

        MyServer.verifyAndSetOptions(args);

        assertEquals(5000, MyServer.myPort);
        assertEquals("/Users/", MyServer.publicDirectory);
    }

    @Test
    public void printRunningMessageDisplaysThatTheServerIsRunningOnTheCorrectPort() {
        MyServer.printRunningMessage();

        assertEquals("Server running on port " + MyServer.myPort + System.lineSeparator(), outContent.toString());
    }

}
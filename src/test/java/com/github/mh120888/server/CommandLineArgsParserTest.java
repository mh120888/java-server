package com.github.mh120888.server;

import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;
import java.security.Security;
import java.util.HashMap;
import static org.junit.Assert.*;

public class CommandLineArgsParserTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private SecurityManager securityManager;

    @org.junit.Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        MyServer.myPort = 3000;
        MyServer.publicDirectory = "~/";

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
    }

    @Test
    public void groupOptionsMatchesSingleFlagWithValue() {
        String[] args = {"-f",  "somethingRandom"};
        HashMap<String, String> parsedArgs = CommandLineArgsParser.groupOptions(args);

        assertTrue(parsedArgs.containsKey("-f"));
        assertEquals("somethingRandom", parsedArgs.get("-f"));
    }

    @Test
    public void groupOptionsMatchesMultipleFlagsWithCorrectValues() {
        String[] args = {"-f",  "somethingRandom", "-ff", "evenMoreRandom"};
        HashMap<String, String> parsedArgs = CommandLineArgsParser.groupOptions(args);

        assertTrue(parsedArgs.containsKey("-ff"));
        assertEquals("evenMoreRandom", parsedArgs.get("-ff"));
    }

    @Test
    public void groupOptionsReturnsAnEmptyHashmapIfGivenAnEmptyArray() {
        String[] args = {};
        HashMap<String, String> parsedArgs = CommandLineArgsParser.groupOptions(args);

        assertEquals(0, parsedArgs.size());
    }

    @Test
    public void groupOptionsIgnoresFlagsWithoutALeadingDash() {
        String[] args = {"f",  "somethingRandom", "-ff", "evenMoreRandom"};
        HashMap<String, String> parsedArgs = CommandLineArgsParser.groupOptions(args);

        assertFalse(parsedArgs.containsKey("f"));
        assertTrue(parsedArgs.containsKey("-ff"));
    }

    @Test
    public void groupOptionsReturnsAnEmptyHashmapIfArgsAreNotInPairs() {
        String[] args = {"-oneflag", "secondValue", "-flagWithNoValue"};
        HashMap<String, String> parsedArgs = new HashMap<>();
        try {
            parsedArgs = CommandLineArgsParser.groupOptions(args);
        } catch (SecurityException e) {

        } finally {
            assertEquals(0, parsedArgs.size());
        }
    }

    @Test
    public void groupOptionsWillExitWithStatusCode64IfArgsAreNotInPairs() {
        SecurityException exception = null;
        try {
            CommandLineArgsParser.printUsageAndExit();
        } catch (SecurityException e) {
            exception = e;
        } finally {
            assertEquals("System exit requested with error 64", exception.getMessage());
        }
    }

    @Test
    public void groupOptionsDisplaysErrorMessageIfArgsAreNotInPairs() {
        String[] args = {"-oneflag", "secondValue",  "-flagWithNoValue"};

        try {
            CommandLineArgsParser.groupOptions(args);
        } catch (SecurityException e) {

        } finally {
            assertFalse(errContent.toString().isEmpty());
        }
    }

}
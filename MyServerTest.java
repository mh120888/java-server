import org.junit.Test;

import java.io.*;

import java.net.*;
import java.util.Arrays;

import static org.junit.Assert.*;

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
    public void testValidateMethodWithValidMethod() {
        boolean result = MyServer.validateMethod("GET");
        assertTrue("Valid method should return true", result);
    }

    @Test
    public void testValidateMethodWithAnotherValidMethod() {
        boolean result = MyServer.validateMethod("PUT");
        assertTrue("Valid method should return true", result);
    }

    @Test
    public void testValidateMethodWithInvalidMethod() {
        boolean result = MyServer.validateMethod("NOTVALID");
        assertFalse("Invalid method should return false", result);
    }
}
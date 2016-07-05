import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

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
    public void respondsWith200() throws IOException {
        MyServer.main(new String[] {"something"});
        assertEquals("HTTP/1.1 200 OK\n", outContent.toString());
    }
}
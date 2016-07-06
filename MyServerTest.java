import org.junit.Test;

import java.io.*;
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
    public void fileTest() {
        File file = new File("/Users/matthewhiggins/Desktop/cob_spec/public");
        String[] fileNames = file.list();
        boolean result = Arrays.asList(fileNames).contains("/" + "file2");
        boolean fileExists = file.exists();
        assertTrue("Does not contain the specified file", result);
    }
}
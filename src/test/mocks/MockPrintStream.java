package mocks;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public class MockPrintStream extends PrintStream {
    public String lastMessage;

    public MockPrintStream(OutputStream out) throws Exception {
        super(out);
    };

    public void println(String message) {
        lastMessage = message;
    }
}

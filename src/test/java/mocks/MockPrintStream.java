package mocks;

import java.io.OutputStream;
import java.io.PrintStream;

public class MockPrintStream extends PrintStream {
    public String lastMessage;

    public MockPrintStream(OutputStream out) throws Exception {
        super(out);
    };

    public void println(String message) {
        lastMessage = message;
    }
    public void write(byte[] message) {
        lastMessage = new String(message);
    }
}

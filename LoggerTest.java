import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/12/16.
 */
public class LoggerTest {
    @Test
    public void requestLogContainsPreviousRequest() {
        String request = "POST / HTTP/1.1";
        Logger.addLog(request);
        List<String> log = Logger.getLog();
        assertEquals(true, log.contains(request));
    }

    @Test
    public void clearLoggerEmptiesTheLog() {
        String request = "POST / HTTP/1.1";
        Logger.addLog(request);
        Logger.clearLog();

        List<String> log = Logger.getLog();
        assertEquals(false, log.contains(request));
    }
}
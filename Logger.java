import java.util.ArrayList;
import java.util.List;

/**
 * Created by matthewhiggins on 7/12/16.
 */
public class Logger {
    public static List<String> requestLog = new ArrayList<String>() {};

    public static List<String> getLog() {
        return requestLog;
    }

    public static void addLog(String request) {
        requestLog.add(request);
    }
}

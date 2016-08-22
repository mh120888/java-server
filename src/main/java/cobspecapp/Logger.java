package cobspecapp;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    public static List<String> requestLog = new ArrayList<String>() {};

    public static List<String> getLog() {
        return requestLog;
    }

    public static void addLog(String request) {
        requestLog.add(request);
    }

    public static void clearLog() {
        requestLog = new ArrayList<String>() {};
    }
}

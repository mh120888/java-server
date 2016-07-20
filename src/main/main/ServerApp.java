package main;

import cobspecapp.CobSpecApp;
import server.MyServer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/20/16.
 */
public class ServerApp {
    private static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";
    private static int port = 5000;

    public static void main (String[] args) throws IOException {
        setOptions(args);
        MyServer.go(port, new CobSpecApp(publicDirectory));
    }

    private static void setOptions(String[] args) {
        HashMap<String, String> options = CommandLineArgsParser.groupOptions(args);
        port = setPortNumber(options);
        setPublicDirectory(options);
    }

    private static int setPortNumber(HashMap<String, String> options) {
        int port = 5000;
        if (options.containsKey("-p")) {
            try {
                port = Integer.parseInt(options.get("-p"));
            } catch (NumberFormatException e) {
                port = 5000;
            }
        }
        return port;
    }

    private static void setPublicDirectory(HashMap<String, String> options) {
        String directory = options.get("-d");
        if (directory == null) {
            return;
        }
        File file = new File(directory);
        if (file.exists()) {
            publicDirectory = directory;
        }
    }
}

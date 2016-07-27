package server; /**
 * Created by matthewhiggins on 7/5/16.
 */

import app.Application;
import cobspecapp.CobSpecApp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
    private static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";
    private static int port = 5000;

    static ExecutorService executor = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws IOException {
        setOptions(args);
        runServer(new CobSpecApp(publicDirectory));
    }

    public static void runServer(Application app) throws IOException {
        ServerSocket server = new ServerSocket(port);
        try {
            while (true) {
                Socket socket = server.accept();
                Runnable connectionHandler = new ConnectionHandler(socket, app);
                executor.execute(connectionHandler);
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port);
            System.exit(-1);
        } finally {
            server.close();
        }
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

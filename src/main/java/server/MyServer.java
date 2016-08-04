package server; /**
 * Created by matthewhiggins on 7/5/16.
 */

import app.Application;
import cobspecapp.CobSpecApp;
import basichttpmessage.BasicHTTPMessageFactory;
import httpmessage.HTTPMessageFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
    static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";
    static int myPort = 5000;
    static Application app = new CobSpecApp(publicDirectory);

    private static ExecutorService executor = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws IOException {
        setOptions(args);
        runServer(app, new BasicHTTPMessageFactory());
    }

    public static void runServer(Application app, HTTPMessageFactory requestResponseFactory) throws IOException {
        ServerSocket server = new ServerSocket(myPort);
        try {
            while (true) {
                Socket socket = server.accept();
                Runnable connectionHandler = ConnectionHandler.getNewConnectionHandler(socket, app, requestResponseFactory);
                executor.execute(connectionHandler);
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + myPort);
            System.exit(-1);
        } finally {
            server.close();
        }
    }

    public static void setOptions(String[] args) {
        HashMap<String, String> options = CommandLineArgsParser.groupOptions(args);
        myPort = getPortNumber(options);
        publicDirectory = setPublicDirectory(options);
    }

     static int getPortNumber(HashMap<String, String> options) {
        int port = myPort;
        if (options.containsKey("-p")) {
            try {
                port = Integer.parseInt(options.get("-p"));
            } catch (NumberFormatException e) {
                port = myPort;
            }
        }
        return port;
    }

    static String setPublicDirectory(HashMap<String, String> options) {
        String directory = publicDirectory;
        String directoryFromOptions = options.get("-d");
        if (directoryFromOptions == null) {
            directory = publicDirectory;
        } else {
            File file = new File(directoryFromOptions);
            if (file.exists()) {
                directory = directoryFromOptions;
            }
        }
        return directory;
    }
}

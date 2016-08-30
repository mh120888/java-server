package com.github.mh120888.server;

import com.github.mh120888.app.Application;
import com.github.mh120888.basichttpmessage.BasicHTTPMessageFactory;
import com.github.mh120888.cobspecapp.CobSpecApp;
import com.github.mh120888.httpmessage.HTTPMessageFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
    static final int DEFAULTPORT = 5000;

    static String publicDirectory;
    static int myPort = DEFAULTPORT;

    private static ExecutorService executor = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws IOException {
        verifyAndSetOptions(args);
        runServer(new CobSpecApp(publicDirectory), new BasicHTTPMessageFactory());
    }

    static void runServer(Application app, HTTPMessageFactory requestResponseFactory) throws IOException {
        ServerSocket server = new ServerSocket(myPort);
        printRunningMessage();
        try {
            while (true) {
                Socket socket = server.accept();
                Runnable connectionHandler = new ConnectionHandler(socket, app, requestResponseFactory);
                executor.execute(connectionHandler);
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + myPort);
            System.exit(-1);
        } finally {
            server.close();
        }
    }

    static void verifyAndSetOptions(String[] args) {
        HashMap<String, String> options = CommandLineArgsParser.groupOptions(args);
        if (isDirectoryOptionPresentAndValid(options)) {
            myPort = getPortNumber(options);
            publicDirectory = getPublicDirectory(options);
        } else {
            CommandLineArgsParser.printUsageAndExit();
        }
    }

    static void printRunningMessage() {
        System.out.println("Server running on port " + myPort);
    }

    private static boolean isDirectoryOptionPresentAndValid(HashMap<String, String> options) {
        return (isDirectoryOptionPresent(options) && isDirectoryOptionValid(options));
    }

    private static boolean isDirectoryOptionPresent(HashMap<String, String> options) {
        return options.containsKey("-d");
    }

    private static boolean isDirectoryOptionValid(HashMap<String, String> options) {
        File file = new File(options.get("-d"));
        return file.exists();
    }

    static int getPortNumber(HashMap<String, String> options) {
        int port = myPort;
        if (options.containsKey("-p")) {
            try {
                port = Integer.parseInt(options.get("-p"));
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage()) ;
            }
        }
        return port;
    }

    static String getPublicDirectory(HashMap<String, String> options) {
        return options.get("-d");
    }
}

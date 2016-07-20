package server; /**
 * Created by matthewhiggins on 7/5/16.
 */

import app.Application;
import cobspecapp.CobSpecApp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MyServer {
    private static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";

    private static int port = 5000;
    private static String host = "localhost";
    private static String protocol = "http";

    public static void main(String[] args) throws IOException {
        setOptions(args);
        runServer(new CobSpecApp(publicDirectory));
    }

    public static void runServer(Application app) throws IOException {
        ServerSocket server = new ServerSocket(port);
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    generateOutput(readInInput(in), new PrintStream(socket.getOutputStream()), app);
                } finally {
                    socket.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port);
            System.exit(-1);
        } finally {
            server.close();
        }
    }

    public static void generateOutput(String input, PrintStream out, Application app) throws IOException {
        HashMap<String, String> response = app.getResponse(HTTPRequestParser.parse(input));
        String output = HTTPResponseBuilder.buildResponse(response);
        out.println(output);
    }

    public static String readInInput(BufferedReader in) throws IOException {
        String input = "";
        String currentLine = in.readLine();
        while (currentLine != null && !currentLine.trim().isEmpty()) {
            input += currentLine.trim() + "\n";
            currentLine = in.readLine();
        }
        return input;
    }

    public static String getLocation(String path) {
        String location = protocol + "://" + host + ":" + Integer.toString(port) + "/";
        return location;
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



//maybe these go into the same package with the server?
//depends on request parser
//depends on http response builder

//maybe instead of depending on a router, and then on the rh interface, i can create an abstraction called Application that simply responds to a method called getResponse, given the request data
//depends on router
//depends on resource handler interface (but not implementations)

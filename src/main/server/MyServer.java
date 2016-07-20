package server; /**
 * Created by matthewhiggins on 7/5/16.
 */

import app.Application;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MyServer {
    private static int port = 5000;
    private static String host = "localhost";
    private static String protocol = "http";

    public static void go(int portNumber, Application app) throws IOException {
        port = portNumber;
        runServer(app);
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
}

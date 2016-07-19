package server; /**
 * Created by matthewhiggins on 7/5/16.
 */

import cobspecapp.ResourceHandler;
import cobspecapp.Router;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MyServer {
    public static String FILEPATH = "/Users/matthewhiggins/Desktop/cob_spec/public";

    private static int port = 5000;
    private static String host = "localhost";
    private static String protocol = "http";

    public static void main(String[] args) throws IOException {
        HashMap<String, String> options = CommandLineArgsParser.groupOptions(args);
        port = getPortNumber(options);
        ServerSocket server = new ServerSocket(port);
        updateFilepath(options);
        try {
            while (true) {
                Socket socket = server.accept();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                try {
                    String input = "";
                    String currentLine = in.readLine().trim();
                    while (!currentLine.isEmpty()) {
                        input += currentLine + "\n";
                        currentLine = in.readLine();
                    }
                    HashMap<String, String> parsedRequest = HTTPRequestParser.parse(input);
                    ResourceHandler resourceHandler = Router.getEndpoint(parsedRequest);
                    HashMap<String, String> responseData = resourceHandler.getResponseData(parsedRequest);
                    String output = HTTPResponseBuilder.buildResponse(responseData);
                    out.println(output);
                } finally {
                    socket.close();
                }
            }
        }
        catch (IOException e) {
            System.out.println("Could not listen on port " + port);
            System.exit(-1);
        } finally {
            server.close();
        }
    }

    public static String getLocation(String path) {
        String location = protocol + "://" + host + ":" + Integer.toString(port) + "/";
        return location;
    }

    private static int getPortNumber(HashMap<String, String> options) {
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

    private static void updateFilepath(HashMap<String, String> options) {
        String directory = options.get("-d");
        if (directory == null) {
            return;
        }
        File file = new File(directory);
        if (file.exists()) {
            FILEPATH = directory;
        }
    }
}

//maybe these go into the same package with the server?
//depends on request parser
//depends on http response builder

//maybe instead of depending on a router, and then on the rh interface, i can create an abstraction called Application that simply responds to a method called getResponse, given the request data
//depends on router
//depends on resource handler interface (but not implementations)

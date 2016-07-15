/**
 * Created by matthewhiggins on 7/5/16.
 */
import com.sun.corba.se.spi.activation.Server;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class MyServer {
    public static String FILEPATH = "/Users/matthewhiggins/Desktop/cob_spec/public";

    public static void main(String[] args) throws IOException {
        HashMap<String, String> options = CommandLineArgsParser.groupOptions(args);
        int port = getPortNumber(options);
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



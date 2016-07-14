/**
 * Created by matthewhiggins on 7/5/16.
 */
import java.io.*;
import java.net.*;
import java.util.HashMap;

public class MyServer {

    private static int port = 5000;
    private static String host = "localhost";
    private static String protocol = "http";

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(port);
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
            System.out.println("Could not listen on port 5000");
            System.exit(-1);
        } finally {
            server.close();
        }
    }

    public static String getLocation(String path) {
        String location = protocol + "://" + host + ":" + Integer.toString(port) + "/";
        return location;
    }
}
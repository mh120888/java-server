/**
 * Created by matthewhiggins on 7/5/16.
 */
import com.sun.corba.se.spi.activation.Server;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class MyServer {

    public static void main(String[] args) throws IOException {
        HashMap<String, String> options = CommandLineArgsParser.groupOptions(args);
        ServerSocket server = createNewServerSocket(options);
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

    private static ServerSocket createNewServerSocket(HashMap<String, String> options) throws IOException {
        int port = 5000;
        if (options.containsKey("-p")) {
            try {
                port = Integer.parseInt(options.get("-p"));
            } catch (NumberFormatException e) {
                port = 5000;
            }
        }
        return new ServerSocket(port);
    }
}

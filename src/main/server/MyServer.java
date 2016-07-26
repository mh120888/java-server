package server; /**
 * Created by matthewhiggins on 7/5/16.
 */

import abstracthttpresponse.AbstractHTTPResponse;
import abstracthttprequest.AbstractHTTPRequest;
import app.Application;
import cobspecapp.CobSpecApp;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MyServer {
    private static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";
    private static int port = 5000;

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
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    HTTPRequest request = new HTTPRequest(readInFirstLineAndHeaders(reader));
                    if (reader.ready() && request.containsHeader("Content-Length")) {
                        int contentLength = Integer.parseInt(request.getHeader("Content-Length"));
                        String body = (readInBody(reader, contentLength));
                        request.setBody(body);
                    }
                    generateOutput(request, new PrintStream(socket.getOutputStream()), app);
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

    public static void generateOutput(AbstractHTTPRequest request, PrintStream out, Application app) {
        AbstractHTTPResponse response = app.getResponse(request, new HTTPResponse());
        try {
            out.write(response.getAllButBody().getBytes());
            out.write(response.getBody());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static String readInFirstLineAndHeaders(BufferedReader br) {
        String input = "";
        try {
            String currentLine = br.readLine();
            while (currentLine != null && !currentLine.trim().isEmpty()) {
                input += currentLine.trim() + "\n";
                currentLine = br.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return input;
    }

    static String readInBody(BufferedReader reader, int contentLength) {
        char[] bodyInChars = new char[contentLength];
        try {
            reader.read(bodyInChars);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return new String(bodyInChars);
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

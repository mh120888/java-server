package server;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import app.Application;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by matthewhiggins on 7/27/16.
 */
public class ConnectionHandler implements Runnable {

    private Socket clientSocket;
    private Application application;

    public ConnectionHandler(Socket socket, Application app) {
        clientSocket = socket;
        application = app;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            HTTPRequest request = new HTTPRequest(readInFirstLineAndHeaders(reader));
            if (reader.ready() && request.containsHeader("Content-Length")) {
                request.setBody(readInBody(reader, Integer.parseInt(request.getHeader("Content-Length"))));
            }
            generateOutput(request, new PrintStream(clientSocket.getOutputStream()), application);
            clientSocket.close();
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

    public static void generateOutput(AbstractHTTPRequest request, PrintStream out, Application app) {
        AbstractHTTPResponse response = app.getResponse(request, new HTTPResponse());
        try {
            out.write(response.getAllButBody().getBytes());
            out.write(response.getBody());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

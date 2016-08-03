package server;

import httpmessage.HTTPRequest;
import httpmessage.HTTPMessageFactory;
import httpmessage.HTTPResponse;
import app.Application;

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
    private HTTPMessageFactory httpMessageFactory;

    private ConnectionHandler(Application app, HTTPMessageFactory httpMessageFactory) {
        this.application = app;
        this.httpMessageFactory = httpMessageFactory;
    }

    private ConnectionHandler(Socket socket, Application app, HTTPMessageFactory httpMessageFactory) {
        this.clientSocket = socket;
        this.application = app;
        this.httpMessageFactory = httpMessageFactory;
    }

    public static ConnectionHandler getNewConnectionHandler(Socket socket, Application app, HTTPMessageFactory requestResponseFactory) {
        return new ConnectionHandler(socket, app, requestResponseFactory);
    }

    public static ConnectionHandler getNewTestConnectionHandler(Application app, HTTPMessageFactory requestResponseFactory) {
        return new ConnectionHandler(app, requestResponseFactory);
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            HTTPRequest request = buildHttpRequest(reader);
            generateOutput(request, new PrintStream(clientSocket.getOutputStream()));
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    HTTPRequest buildHttpRequest(BufferedReader reader) {
         HTTPRequest request = httpMessageFactory.getNewRequest();
         try {
             request.setRequestLine(readInFirstLine(reader));
             if (reader.ready()) {
                 request.setHeaders(readInHeaders(reader));
             }
             if (reader.ready() && request.containsHeader("Content-Length")) {
                 request.setBody(readInBody(reader, Integer.parseInt(request.getHeader("Content-Length"))));
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
         return request;
     }

    String readInFirstLine(BufferedReader br) throws IOException {
        String input = "";
        input = br.readLine();
        return input.trim();
     }

    String readInHeaders(BufferedReader br) throws IOException {
        String input = "";
        String currentLine = br.readLine();
        while (currentLine != null && !currentLine.trim().isEmpty()) {
            input += currentLine.trim() + "\n";
            currentLine = br.readLine();
        }
        return input;
     }

    String readInBody(BufferedReader reader, int contentLength) throws IOException {
        char[] bodyInChars = new char[contentLength];
        reader.read(bodyInChars);
        return new String(bodyInChars);
     }

     void generateOutput(HTTPRequest request, PrintStream out) throws IOException {
        HTTPResponse response = application.getResponse(request, httpMessageFactory.getNewResponse());
        out.write(response.getStatusLineAndHeaders().getBytes());
        out.write(response.getBody());
    }
}

package server;

import httpresponse.HTTPResponseFactory;
import request.Request;
import request.RequestFactory;
import response.Response;
import app.Application;
import response.ResponseFactory;

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
    private RequestFactory requestFactory;
    private ResponseFactory responseFactory;

    private ConnectionHandler(Application app, RequestFactory requestFactory, ResponseFactory responseFactory) {
        this.application = app;
        this.requestFactory = requestFactory;
        this.responseFactory = responseFactory;
    }

    private ConnectionHandler(Socket socket, Application app, RequestFactory requestFactory, ResponseFactory responseFactory) {
        this.clientSocket = socket;
        this.application = app;
        this.requestFactory = requestFactory;
        this.responseFactory = responseFactory;
    }

    public static ConnectionHandler getNewConnectionHandler(Socket socket, Application app, RequestFactory requestFactory, ResponseFactory responseFactory) {
        return new ConnectionHandler(socket, app, requestFactory, responseFactory);
    }

    public static ConnectionHandler getNewTestConnectionHandler(Application app, RequestFactory requestFactory, ResponseFactory responseFactory) {
        return new ConnectionHandler(app, requestFactory, responseFactory);
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Request request = buildHttpRequest(reader);
            generateOutput(request, new PrintStream(clientSocket.getOutputStream()));
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Request buildHttpRequest(BufferedReader reader) {
         Request request = requestFactory.getNewRequest();
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

     void generateOutput(Request request, PrintStream out) throws IOException {
        Response response = application.getResponse(request, responseFactory.getResponse());
        out.write(response.getStatusLineAndHeaders().getBytes());
        out.write(response.getBody());
    }
}

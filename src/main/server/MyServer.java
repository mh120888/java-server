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

    public static void main(String[] args) throws Exception {
        setOptions(args);
        runServer(new CobSpecApp(publicDirectory));
    }

    public static void runServer(Application app) throws Exception {
        ServerSocket server = new ServerSocket(port);
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    InputStream is = socket.getInputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(is));
                    HTTPRequest request = new HTTPRequest(readInInput(in));
                    if (request.containsHeader("Content-Length")) {
                        BufferedInputStream bis = new BufferedInputStream(is);
                        request.setBody(readInBody(is, 0.125f));
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

    public static void generateOutput(AbstractHTTPRequest request, PrintStream out, Application app) throws IOException {
        AbstractHTTPResponse response = app.getResponse(request, new HTTPResponse());
        out.write(response.getAllButBody().getBytes());
        out.write(response.getBody());
    }

    public static String readInInput(BufferedReader br) throws IOException {
        String input = "";
        String currentLine = br.readLine();
        while (currentLine != null && !currentLine.trim().isEmpty()) {
            input += currentLine.trim() + "\n";
            br.mark(1);
            currentLine = br.readLine();
        }
        br.reset();
        return input;
    }

    static String readInBody(InputStream is, float numOfOctets) throws IOException {
        int numOfBytesToRead = Math.round(numOfOctets*8.0f);
        byte[] resultBytes = new byte[numOfBytesToRead];
        is.read(resultBytes, 0, numOfBytesToRead);
        return new String(resultBytes);
//        return "data=fatcat";
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

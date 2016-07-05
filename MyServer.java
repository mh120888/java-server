/**
 * Created by matthewhiggins on 7/5/16.
 */
import java.io.*;
import java.net.*;

public class MyServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5000);
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    String output = generateHeader();
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

    public static String generateHeader() {
        return "HTTP/1.1 200 OK";
    }
}

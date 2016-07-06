/**
 * Created by matthewhiggins on 7/5/16.
 */
import java.io.*;
import java.net.*;
import java.util.Arrays;

public class MyServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5000);
        try {
            while (true) {
                Socket socket = server.accept();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                try {
                    String input = in.readLine().trim();
                    String output = generateIRL(input);
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

    public static boolean validateMethod(String method) {
        String[] validMethods = {"GET", "POST", "PUT", "PATCH", "DELETE" };
        boolean result = Arrays.asList(validMethods).contains(method);
        return result;
    }

    public static String generateIRL(String input) {
        if (validateMethod(input.split(" ")[0])) {
            return "HTTP/1.1 200 OK\n";
        } else {
            return "HTTP/1.1 404 Not Found\n";
        }
    }
}

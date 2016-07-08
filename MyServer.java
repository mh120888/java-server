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
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            try {
                String input = in.readLine().trim();
                MyResponse response = new MyResponse(new MyRequest(input));
                String output = response.buildResponse();
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
}

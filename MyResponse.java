/**
 * Created by matthewhiggins on 7/6/16.
 */
public class MyResponse {
    private MyRequest request;

    public MyResponse(MyRequest inputRequest) {
        request = inputRequest;
    }

    public String getResponse() {
        if (request.validateMethod() && request.validatePath()) {
            return "HTTP/1.1 200 OK\n";
        } else if (!request.validatePath()) {
            return "HTTP/1.1 404 Not Found\n";
        } else {
            return "HTTP/1.1 405 Method Not Allowed\n";
        }
    }
}

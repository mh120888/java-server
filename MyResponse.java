/**
 * Created by matthewhiggins on 7/6/16.
 */
public class MyResponse {
    private MyRequest request;

    public MyResponse(MyRequest inputRequest) {
        request = inputRequest;
    }

    public String buildResponse() {
        String response = "";
        response += buildInitialResponseLine();
        response += "\n\n";
        response += buildBody();

        return response;
    }

    private String buildInitialResponseLine() {
        String response = "HTTP/1.1 ";
        if (request.isCoffee()) {
            response += "418";
        } else if (request.validateMethod() && request.validatePath()) {
            response += "200 OK";
        } else if (!request.validatePath()) {
            response += "404 Not Found";
        } else {
            response += "405 Method Not Allowed";
        }
        return response;
    }

    private String buildBody() {
        String response = "";
        if (request.isCoffee()) {
            response += "I'm a teapot";
        }
        return response;
    }
}

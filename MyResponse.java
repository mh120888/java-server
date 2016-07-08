import java.io.File;

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
        response += buildHeaders();
        response += "\n\n";
        response += buildBody();

        return response;
    }

    private String buildInitialResponseLine() {
        String response = "HTTP/1.1 ";
        if (request.requiresAuthorization()) {
            response += "401 Unauthorized";
        } else if (request.isCoffee()) {
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

    private String buildHeaders() {
        String response = "";
        if (request.requiresAuthorization()) {
            response += "\nWWW-Authenticate: Basic realm=\"User Visible Realm\"";
        }
        return response;
    }

    private String buildBody() {
        String response = "";
        if (request.isCoffee()) {
            response += "I'm a teapot";
        } else if (request.path.equals("/")) {
            File file = new File("/Users/matthewhiggins/Desktop/cob_spec/public");
            String[] fileNames = file.list();
            for (String fileName : fileNames) {
                response += ("<a href=\"/" + fileName + "\">" + fileName + "</a>\n");
            }
        }
        return response;
    }
}

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/12/16.
 */
public class LogsResourceHandler implements ResourceHandler {
    private String correctCredentials = "admin:hunter2";

    public HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        HashMap<String, String> response = new HashMap<>();

        if (isAuthorized(requestData)) {
            response.put("responseLine", "HTTP/1.1 200 OK");
            response.put("body", String.join("\n", Logger.getLog()));
        } else {
            response.put("responseLine", "HTTP/1.1 401 Unauthorized");
            response.put("headers", "WWW-Authenticate: Basic realm=\"User Visible Realm\"");
        }
        return response;
    }

    public boolean isAuthorized(HashMap<String, String> requestData) {
        String encodedCredentials = "";
        if (requestData.containsKey("headers")) {
            HashMap<String, String> parsedHeaders = HeaderParser.parse(requestData.get("headers"));
            if (parsedHeaders.containsKey("Authorization")) {
                encodedCredentials = parsedHeaders.get("Authorization").replace("Basic ", "");
            }
        }
        return BasicAuthorizer.isAuthorized(encodedCredentials, correctCredentials);
    }
}

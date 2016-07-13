import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/12/16.
 */
public class LogsEndpoint implements Endpoint {
    public HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        HashMap<String, String> response = new HashMap<>();

        if (requestData.containsKey("headers")) {
            response.put("responseLine", "HTTP/1.1 200 OK");
            response.put("body", String.join("\n", Logger.getLog()));
        } else {
            response.put("responseLine", "HTTP/1.1 401 Unauthorized");
            response.put("headers", "WWW-Authenticate: Basic realm=\"User Visible Realm\"");
        }
        return response;
    }
}

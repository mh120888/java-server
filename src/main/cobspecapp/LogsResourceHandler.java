package cobspecapp;

import abstracthttprequest.AbstractHttpRequest;

import java.util.HashMap;

/**L
 * Created by matthewhiggins on 7/12/16.
 */
public class LogsResourceHandler implements ResourceHandler {
    private String correctCredentials = "admin:hunter2";

    public HashMap<String, String> getResponseData(AbstractHttpRequest requestData) {
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

    public boolean isAuthorized(AbstractHttpRequest requestData) {
        String encodedCredentials = "";
        if (requestData.headerExists("Authorization")) {
            encodedCredentials = requestData.getHeader("Authorization").replace("Basic ", "");
        }
        return BasicAuthorizer.isAuthorized(encodedCredentials, correctCredentials);
    }
}

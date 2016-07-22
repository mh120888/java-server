package server;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class HTTPResponseBuilder {
    public static String buildResponse(HashMap<String, String> request) {
        String response = "";
        response += request.get("responseLine");
        if (request.containsKey("headers")) {
            response += "\n";
            response += request.get("headers");
        }
        if (request.containsKey("body")) {
            response += "\n\n";
            response += request.get("body");
        }
        return response;
    }
}

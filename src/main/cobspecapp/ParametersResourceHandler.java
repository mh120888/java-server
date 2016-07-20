package cobspecapp;

import server.ParameterParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class ParametersResourceHandler implements ResourceHandler {
    public HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        HashMap<String, String> responseData = new HashMap<>();
        String method = requestData.get("method");

        if (method.equals("GET")) {
            responseData.put("responseLine", "HTTP/1.1 200 OK");
        } else {
            responseData.put("responseLine", "HTTP/1.1 405 Method Not Allowed");
        }

        String path = requestData.get("path");
        Map<String, String> parameters = ParameterParser.parse(path);

        if (parameters.size() > 0) {
            String body = "";
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                body += entry.getKey() + " = " + entry.getValue() + "\n";
            }
            responseData.put("boy", body);
        }

        return responseData;
    }
}

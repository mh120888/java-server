package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class ParametersResourceHandler implements ResourceHandler {

    public HashMap<String, String> getResponseData(AbstractHTTPRequest requestData) {
        HashMap<String, String> responseData = new HashMap<>();

        if (requestData.getMethod().equals("GET")) {
            responseData.put("responseLine", "HTTP/1.1 200 OK");
        } else {
            responseData.put("responseLine", "HTTP/1.1 405 Method Not Allowed");
        }

        Map<String, String> parameters = requestData.getAllParams();

        if (parameters.size() > 0) {
            String body = "";
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                body += entry.getKey() + " = " + entry.getValue() + "\n";
            }
            responseData.put("body", body);
        }

        return responseData;
    }
}

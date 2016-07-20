package cobspecapp;

import abstracthttprequest.AbstractHttpRequest;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class OptionsResourceHandler implements ResourceHandler {

    public HashMap<String, String> getResponseData(AbstractHttpRequest requestData) {
        HashMap<String, String> responseData = new HashMap<>();

        responseData.put("responseLine", "HTTP/1.1 200 OK");
        responseData.put("headers", buildHeaders(requestData.getPath()));

        return responseData;
    }

    private String buildHeaders(String path) {
        String header = "";
        header += "Allow: " + String.join(",", acceptedMethods(path));
        return header;
    }

    private String[] acceptedMethods(String path) {
        switch (path) {
            case "/method_options": return new String[] {"GET","HEAD","POST","OPTIONS","PUT"};
            default:                return new String[] {"GET","OPTIONS"};
        }
    }
}

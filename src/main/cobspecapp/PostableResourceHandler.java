package cobspecapp;

import abstracthttprequest.AbstractHttpRequest;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class PostableResourceHandler implements ResourceHandler {
    private static String data = "";

    public HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        HashMap<String, String> responseData = new HashMap<>();

        if (requestData.get("method").equals("GET")) {
            responseData.put("responseLine", "HTTP/1.1 200 OK");
            responseData.put("body", data);
        } else if (requestData.containsKey("body")) {
            responseData.put("responseLine", "HTTP/1.1 200 OK");
            data = requestData.get("body");
        } else {
            responseData.put("responseLine", "HTTP/1.1 405 Method Not Allowed");
        }
        return responseData;
    }

    public HashMap<String, String> getResponseData(AbstractHttpRequest requestData) {
        HashMap<String, String> responseData = new HashMap<>();

        if (requestData.getMethod().equals("GET")) {
            responseData.put("responseLine", "HTTP/1.1 200 OK");
            responseData.put("body", data);
        }
//        else if (requestData.containsKey("body")) {
//            responseData.put("responseLine", "HTTP/1.1 200 OK");
//            data = requestData.get("body");
//        }
        else {
                responseData.put("responseLine", "HTTP/1.1 405 Method Not Allowed");
        }
        return responseData;
    }
}
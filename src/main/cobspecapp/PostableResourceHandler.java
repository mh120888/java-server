package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class PostableResourceHandler implements ResourceHandler {
    private static String data = "";

//    public HashMap<String, String> getResponseData(AbstractHTTPRequest requestData) {
//        HashMap<String, String> responseData = new HashMap<>();
//
//        if (requestData.getMethod().equals("GET")) {
//            responseData.put("responseLine", "HTTP/1.1 200 OK");
//            responseData.put("body", data);
//        }
////        else if (requestData.containsKey("body")) {
////            responseData.put("responseLine", "HTTP/1.1 200 OK");
////            data = requestData.get("body");
////        }
//        else {
//                responseData.put("responseLine", "HTTP/1.1 405 Method Not Allowed");
//        }
//        return responseData;
//    }

    public String getResponseData(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        if (request.getMethod().equals("GET")) {
            response.setStatus(200);
            response.setBody(data);
        }
//        else if (requestData.containsKey("body")) {
//            responseData.put("responseLine", "HTTP/1.1 200 OK");
//            data = requestData.get("body");
//        }
        else {
            response.setStatus(405);
        }
        return response.getFormattedResponse();
    }
}
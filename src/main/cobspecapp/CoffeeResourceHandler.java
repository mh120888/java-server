package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class CoffeeResourceHandler implements ResourceHandler {
    public CoffeeResourceHandler() {}

//    public HashMap<String, String> getResponseData(AbstractHTTPRequest requestData) {
//        HashMap<String, String> responseData = new HashMap<>();
//
//        if (requestData.getPath().equals("/coffee")) {
//            responseData.put("responseLine", "HTTP/1.1 418");
//            responseData.put("body", "I'm a teapot");
//        } else {
//            responseData.put("responseLine", "HTTP/1.1 200 OK");
//        }
//
//        return responseData;
//    }

    public String getResponseData(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        if (request.getPath().equals("/coffee")) {
            response.setStatus(418);
            response.setBody("I'm a teapot");
        } else {
            response.setStatus(200);
        }
        return response.getFormattedResponse();
    }
}

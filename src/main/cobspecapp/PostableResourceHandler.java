package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class PostableResourceHandler implements ResourceHandler {
    private static String data = "";

    public AbstractHTTPResponse getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        if (request.getMethod().equals("GET")) {
            response.setStatus(200);
            response.setBodyFromString(data);
        }
//        else if (requestData.containsKey("body")) {
//            responseData.put("responseLine", "HTTP/1.1 200 OK");
//            data = requestData.get("body");
//        }
        else {
            response.setStatus(405);
        }
        return response;
    }
}
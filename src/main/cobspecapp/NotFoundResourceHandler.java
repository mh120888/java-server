package cobspecapp;

import abstracthttprequest.AbstractHttpRequest;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class NotFoundResourceHandler implements ResourceHandler {
    public NotFoundResourceHandler() {}

    public HashMap<String, String> getResponseData(AbstractHttpRequest requestData) {
        HashMap<String, String> responseData = new HashMap<>();
        responseData.put("responseLine", "HTTP/1.1 404 Not Found");

        return responseData;
    }
}

package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class OptionsResourceHandler implements ResourceHandler {

    public String getResponseData(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(200);
        response.addHeader("Allow", buildHeaders(request.getPath()));

        return response.getFormattedResponse();
    }

    private String buildHeaders(String path) {
        String header = "";
        header += String.join(",", acceptedMethods(path));
        return header;
    }

    private String[] acceptedMethods(String path) {
        switch (path) {
            case "/method_options": return new String[] {"GET","HEAD","POST","OPTIONS","PUT"};
            default:                return new String[] {"GET","OPTIONS"};
        }
    }
}

package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import response.Response;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class OptionsAction implements Action {

    public Response getResponse(AbstractHTTPRequest request, Response response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(200);
        response.addHeader("Allow", buildHeaders(request.getPath()));

        return response;
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

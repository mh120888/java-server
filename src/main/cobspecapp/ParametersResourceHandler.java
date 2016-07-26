package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

import java.util.Map;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class ParametersResourceHandler implements ResourceHandler {

    public AbstractHTTPResponse getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion(request.getVersion());

        if (request.getMethod().equals("GET")) {
            response.setStatus(200);
        } else {
            response.setStatus(405);
        }

        Map<String, String> parameters = request.getAllParams();

        if (parameters.size() > 0) {
            String body = "";
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                body += entry.getKey() + " = " + entry.getValue() + "\n";
            }
            response.setBodyFromString(body);
        }

        return response;
    }
}

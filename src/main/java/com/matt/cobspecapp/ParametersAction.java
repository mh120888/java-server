package cobspecapp;

import httpmessage.HTTPRequest;
import httpmessage.HTTPResponse;

import java.util.Map;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class ParametersAction implements Action {

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
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
            response.setBody(body.getBytes());
        }

        return response;
    }
}

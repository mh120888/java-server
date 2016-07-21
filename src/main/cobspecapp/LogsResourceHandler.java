package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

import java.util.HashMap;

/**L
 * Created by matthewhiggins on 7/12/16.
 */
public class LogsResourceHandler implements ResourceHandler {
    private String correctCredentials = "admin:hunter2";

    public String getResponseData(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion(request.getVersion());

        if (isAuthorized(request)) {
            response.setStatus(200);
            response.setBody(String.join("\n", Logger.getLog()));
        } else {
            response.setStatus(401);
            response.addHeader("WWW-Authenticate", "Basic realm=\"User Visible Realm\"");
        }
        return response.getFormattedResponse();
    }

    public boolean isAuthorized(AbstractHTTPRequest request) {
        String encodedCredentials = "";
        if (request.headerExists("Authorization")) {
            encodedCredentials = request.getHeader("Authorization").replace("Basic ", "");
        }
        return BasicAuthorizer.isAuthorized(encodedCredentials, correctCredentials);
    }
}

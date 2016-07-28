package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import response.Response;

/**L
 * Created by matthewhiggins on 7/12/16.
 */
public class LogsAction implements Action {
    private String correctCredentials = "admin:hunter2";

    public Response getResponse(AbstractHTTPRequest request, Response response) {
        response.setHTTPVersion(request.getVersion());

        if (isAuthorized(request)) {
            response.setStatus(200);
            response.setBodyFromString(String.join("\n", Logger.getLog()));
        } else {
            response.setStatus(401);
            response.addHeader("WWW-Authenticate", "Basic realm=\"User Visible Realm\"");
        }
        return response;
    }

    public boolean isAuthorized(AbstractHTTPRequest request) {
        String encodedCredentials = "";
        if (request.containsHeader("Authorization")) {
            encodedCredentials = request.getHeader("Authorization").replace("Basic ", "");
        }
        return BasicAuthorizer.isAuthorized(encodedCredentials, correctCredentials);
    }
}

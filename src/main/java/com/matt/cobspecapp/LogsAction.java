package cobspecapp;

import request.Request;
import response.Response;

/**L
 * Created by matthewhiggins on 7/12/16.
 */
public class LogsAction implements Action {
    private String correctCredentials = "admin:hunter2";

    public Response getResponse(Request request, Response response) {
        response.setHTTPVersion(request.getVersion());

        if (isAuthorized(request)) {
            response.setStatus(200);
            response.setBody(String.join("\n", Logger.getLog()).getBytes());
        } else {
            response.setStatus(401);
            response.addHeader("WWW-Authenticate", "Basic realm=\"User Visible Realm\"");
        }
        return response;
    }

    public boolean isAuthorized(Request request) {
        String encodedCredentials = "";
        if (request.containsHeader("Authorization")) {
            encodedCredentials = request.getHeader("Authorization").replace("Basic ", "");
        }
        return BasicAuthorizer.isAuthorized(encodedCredentials, correctCredentials);
    }
}

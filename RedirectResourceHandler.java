import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class RedirectResourceHandler implements ResourceHandler {
    public HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        HashMap<String, String> responseData = new HashMap<>();
        responseData.put("responseLine", "HTTP/1.1 302 Found");
        responseData.put("headers", "location: " + MyServer.getLocation(requestData.get("path")));

        return responseData;
    }
}

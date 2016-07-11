import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class PostableEndpoint implements Endpoint {
    public HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        HashMap<String, String> responseData = new HashMap<>();
        if (requestData.get("method").equals("GET") || requestData.containsKey("body")) {
            responseData.put("responseLine", "HTTP/1.1 200 OK");
        } else {
            responseData.put("responseLine", "HTTP/1.1 405 Method Not Allowed");
        }

        return responseData;
    }
}
import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class CoffeeEndpoint {
    public static HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        String method = requestData.get("method");
        String path = requestData.get("path");

        HashMap<String, String> responseData = new HashMap<>();
        if (path.equals("/coffee")) {
            responseData.put("responseLine", "HTTP/1.1 418");
            responseData.put("body", "I'm a teapot");
        } else {
            responseData.put("responseLine", "HTTP/1.1 200 OK");
        }

        return responseData;
    }
}

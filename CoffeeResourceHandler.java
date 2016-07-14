import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class CoffeeResourceHandler implements ResourceHandler {
    public CoffeeResourceHandler() {}

    public HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        HashMap<String, String> responseData = new HashMap<>();
        String method = requestData.get("method");
        String path = requestData.get("path");

        if (path.equals("/coffee")) {
            responseData.put("responseLine", "HTTP/1.1 418");
            responseData.put("body", "I'm a teapot");
        } else {
            responseData.put("responseLine", "HTTP/1.1 200 OK");
        }

        return responseData;
    }
}

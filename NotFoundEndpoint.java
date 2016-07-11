import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class NotFoundEndpoint implements Endpoint {
    public NotFoundEndpoint() {}

    public HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        HashMap<String, String> responseData = new HashMap<>();
        responseData.put("responseLine", "HTTP/1.1 404 Not Found");

        return responseData;
    }
}

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class OptionsResourceHandler implements ResourceHandler {

    public HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        HashMap<String, String> responseData = new HashMap<>();
        String[] methods = acceptedMethods(requestData.get("path"));

        responseData.put("responseLine", "HTTP/1.1 200 OK");
        responseData.put("headers", buildHeaders(requestData.get("path")));

        return responseData;
    }

    private String buildHeaders(String path) {
        String header = "";
        header += "Allow: " + String.join(",", acceptedMethods(path));
        return header;
    }

    private String[] acceptedMethods(String path) {
        switch (path) {
            case "/method_options": return new String[] {"GET","HEAD","POST","OPTIONS","PUT"};
            default:                return new String[] {"GET","OPTIONS"};
        }
    }
}

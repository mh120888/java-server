import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/14/16.
 */
public class OptionsEndpoint implements Endpoint {

    public HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        HashMap<String, String> responseData = new HashMap<>();
        String[] methods = acceptedMethods(requestData.get("path"));

        responseData.put("responseLine", "HTTP/1.1 200 OK");
        responseData.put("headers", "Allow: " + String.join(",", methods));

        return responseData;
    }

    private String[] acceptedMethods(String path) {
        String [] methodsThatAreAcceptable = {"GET","HEAD","POST","OPTIONS","PUT"};
        if (path.equals("/method_options2")) {
            String[] methods = {"GET","OPTIONS"};
            return methods;
        }
        return methodsThatAreAcceptable;
    }
}

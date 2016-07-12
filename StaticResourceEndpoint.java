import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class StaticResourceEndpoint implements Endpoint {
    public StaticResourceEndpoint() {}

    public HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        HashMap<String, String> responseData = new HashMap<>();
        responseData.put("responseLine", getRequestLine(requestData));
        responseData.put("body", getBody(requestData));

        return responseData;
    }

    private String getRequestLine(HashMap<String, String> requestData) {
        String method = requestData.get("method");
        String path = requestData.get("path");

        if (isAcceptableMethodWithoutParams(method) || requestData.containsKey("body") && isAcceptableMethodWithParams(method)) {
            return "HTTP/1.1 200 OK";
        } else {
            return "HTTP/1.1 405 Method Not Allowed";
        }
    }

    private boolean isAcceptableMethodWithoutParams(String method) {
        String[] acceptableMethodsWithoutParams = {"GET", "HEAD"};

        return isAcceptableMethod(method, acceptableMethodsWithoutParams);
    }

    private boolean isAcceptableMethodWithParams(String method) {
        String[] acceptableMethodsWithParams = {"POST", "PUT"};

        return isAcceptableMethod(method, acceptableMethodsWithParams);
    }

    private boolean isAcceptableMethod(String method, String[] listOfMethods) {
        return Arrays.asList(listOfMethods).contains(method);
    }

    private String getBody(HashMap<String, String> requestData) {
        String method = requestData.get("method");
        String body = "";

        if (method.equals("GET")) {
            File file = new File(Endpoint.FILEPATH);
            String[] fileNames = file.list();

            for (String fileName : fileNames) {
                body += ("<a href=\"/" + fileName + "\">" + fileName + "</a>\n");
            }
        }
        return body;
    }
}

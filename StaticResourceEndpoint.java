import java.io.File;
import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class StaticResourceEndpoint {
    public static HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        HashMap<String, String> responseData = new HashMap<>();
        responseData.put("responseLine", getRequestLine(requestData));
        responseData.put("body", getBody(requestData));

        return responseData;
    };

    private static String getRequestLine(HashMap<String, String> requestData) {
        String method = requestData.get("method");
        String path = requestData.get("path");

        if (method.equals("GET") || ((method.equals("POST") || method.equals("PUT")) && requestData.containsKey("body"))) {
            return "HTTP/1.1 200 OK";
        } else {
            return "HTTP/1.1 405 Method Not Allowed";
        }
    }

    private static String getBody(HashMap<String, String> requestData) {
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

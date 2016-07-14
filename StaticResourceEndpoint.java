import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class StaticResourceEndpoint implements Endpoint {
    public StaticResourceEndpoint() {}

    public enum Filetype {
        DIRECTORY, IMAGE, OTHER
    }

    public HashMap<String, String> getResponseData(HashMap<String, String> requestData) {
        HashMap<String, String> responseData = new HashMap<>();
        responseData.put("responseLine", getResponseLine(requestData));
        responseData.put("body", getBody(requestData));

        return responseData;
    }

    private String getResponseLine(HashMap<String, String> requestData) {
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
        if (!method.equals("GET")) {
            return body;
        }
        String path = requestData.get("path");
        Filetype filetype = getFiletype(path);
        switch (getFiletype(path)) {
            case DIRECTORY:
                File file = new File(Endpoint.FILEPATH);
                String[] fileNames = file.list();

                for (String fileName : fileNames) {
                    body += ("<a href=\"/" + fileName + "\">" + fileName + "</a>\n");
                }
                break;
            default:
                try {
                    String filePath = Endpoint.FILEPATH + path;
                    byte[] imageContents = Files.readAllBytes(Paths.get(filePath));
                    body = new String(imageContents, Charset.defaultCharset());
                } catch (IOException e) {
                    System.err.println(e);
                }
                break;
        }
        return body;
    }

    public Filetype getFiletype(String path) {
        String[] splitUpPath = path.split("\\.");
        String[] imageExtensions = {"png", "jpeg", "gif"};

        if (isPathADirectory(path)) {
            return Filetype.DIRECTORY;
        } else if (Arrays.asList(imageExtensions).contains(splitUpPath[splitUpPath.length - 1])) {
            return Filetype.IMAGE;
        } else {
            return Filetype.OTHER;
        }
    }

    public boolean isPathADirectory(String path) {
        String filePath = Endpoint.FILEPATH + path;
        File file = new File(filePath);
        return file.isDirectory();
    }
}

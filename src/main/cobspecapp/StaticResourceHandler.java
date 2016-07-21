package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;

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
public class StaticResourceHandler implements ResourceHandler {
    public StaticResourceHandler(String filepath) {
        publicDirectory = filepath;
    }

    public static String publicDirectory;

    public enum Filetype {
        DIRECTORY, IMAGE, OTHER
    }

    public HashMap<String, String> getResponseData(AbstractHTTPRequest requestData) {
        HashMap<String, String> responseData = new HashMap<>();
        responseData.put("responseLine", getResponseLine(requestData));
        responseData.put("body", getBody(requestData));

        return responseData;
    }

    private String getResponseLine(AbstractHTTPRequest requestData) {
        String method = requestData.getMethod();

        if (isAcceptableMethodWithoutParams(method))
//        (isAcceptableMethodWithoutParams(method) || requestData.containsKey("body") && isAcceptableMethodWithParams(method))
        {
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

    private String getBody(AbstractHTTPRequest requestData) {
        String method = requestData.getMethod();
        String body = "";
        if (!method.equals("GET")) {
            return body;
        }
        String path = requestData.getPath();
        switch (getFiletype(path)) {
            case DIRECTORY:
                File file = new File(publicDirectory);
                String[] fileNames = file.list();

                for (String fileName : fileNames) {
                    body += ("<a href=\"/" + fileName + "\">" + fileName + "</a>\n");
                }
                break;
            default:
                try {
                    String filePath = publicDirectory + path;
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
        String filePath = publicDirectory + path;
        File file = new File(filePath);
        return file.isDirectory();
    }
}

//depends on cobspecapp.ResourceHandler Interface
//depends on server for filepath
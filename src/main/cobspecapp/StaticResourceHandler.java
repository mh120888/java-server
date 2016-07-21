package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

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

    public String getResponseData(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(getResponseLine(request));
        response.setBody(getBody(request));

        return response.getFormattedResponse();
    }

    private int getResponseLine(AbstractHTTPRequest request) {
        String method = request.getMethod();

        if (isAcceptableMethodWithoutParams(method))
//        (isAcceptableMethodWithoutParams(method) || requestData.containsKey("body") && isAcceptableMethodWithParams(method))
        {
            return 200;
        } else {
            return 405;
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
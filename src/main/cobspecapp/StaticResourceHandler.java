package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

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

    public String getResponse(AbstractHTTPRequest request, AbstractHTTPResponse response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(getResponseLine(request));
        response.setBody(getBody(request));

        return response.getFormattedResponse();
    }

    private int getResponseLine(AbstractHTTPRequest request) {
        String method = request.getMethod();

        if (request.containsHeader("Range")) {
            return 206;
        }
//        (isAcceptableMethodWithoutParams(method) || requestData.containsKey("body") && isAcceptableMethodWithParams(method))
        else if (isAcceptableMethodWithoutParams(method)) {
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

    private String getBody(AbstractHTTPRequest request) {
        String method = request.getMethod();
        String body = "";
        if (!method.equals("GET")) {
            return body;
        }
        String path = request.getPath();
        switch (getFiletype(path)) {
            case DIRECTORY:
                body += getBodyForDirectory();
                break;
            default:
                body += getBodyDefault(request);
                break;
        }
        return body;
    }

    String getBodyForDirectory() {
        String body = "";
        File file = new File(publicDirectory);
        String[] fileNames = file.list();

        for (String fileName : fileNames) {
            body += ("<a href=\"/" + fileName + "\">" + fileName + "</a>\n");
        }
        return body;
    }

     String getBodyDefault(AbstractHTTPRequest request) {
        String body = "";
        try {
            String filePath = publicDirectory + request.getPath();
            byte[] imageContents = getCorrectPortionOfFileContents(Files.readAllBytes(Paths.get(filePath)), request);
            body = new String(imageContents);
        } catch (IOException e) {
            System.err.println(e);
        }
        return body;
    }

     byte[] getCorrectPortionOfFileContents(byte[] fileContents, AbstractHTTPRequest request) {
        byte[] result = fileContents;
        if (request.containsHeader("Range")) {
            int[] range = request.getHeaderParser().parseRangeHeader(request.getHeader("Range"), fileContents);
            result = Arrays.copyOfRange(fileContents, range[0], range[1] + 1);
        }
        return result;
    }

    Filetype getFiletype(String path) {
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

    boolean isPathADirectory(String path) {
        String filePath = publicDirectory + path;
        File file = new File(filePath);
        return file.isDirectory();
    }
}

package cobspecapp;

import request.Request;
import response.Response;

import java.io.File;
import java.util.Arrays;


/**
 * Created by matthewhiggins on 7/11/16.
 */
public class StaticResourceAction implements Action {
    public static String publicDirectory;

    public StaticResourceAction(String filepath) {
        publicDirectory = filepath;
        fileIO = new RealFileIO();
    }

    public StaticResourceAction(String filepath, FileIO fileInputOuput) {
        publicDirectory = filepath;
        fileIO = fileInputOuput;
    }

    FileIO fileIO;

    public enum Filetype {
        DIRECTORY, IMAGE, OTHER
    }

    public Response getResponse(Request request, Response response) {
        response.setHTTPVersion(request.getVersion());
        response.setStatus(getResponseLine(request));
        response.setBody(getBody(request));

        modifyResource(request);

        return response;
    }

    private void modifyResource(Request request) {
        if (isValidPatch(request)) {
            fileIO.writeToFile(publicDirectory + request.getPath(), request.getBody().getBytes());
        }
    }

    private boolean isValidPatch(Request request) {
        return request.getMethod().equals("PATCH") && !request.getBody().isEmpty() && request.containsHeader("If-Match");
    }

    private int getResponseLine(Request request) {
        String method = request.getMethod();

        if (request.containsHeader("Range")) {
            return 206;
        } else if (!request.getBody().isEmpty() && method.equals("PATCH")) {
            return 204;
        } else if (isAcceptableMethodWithoutParams(method)) {
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

    private byte[] getBody(Request request) {
        String method = request.getMethod();
        byte[] body = new byte[0];
        if (!method.equals("GET")) {
            return body;
        }
        String path = request.getPath();
        switch (getFiletype(path)) {
            case DIRECTORY:
                body = getBodyForDirectory();
                break;
            default:
                body = getBodyDefault(request);
                break;
        }
        return body;
    }

    byte[] getBodyForDirectory() {
        String body = "";
        File file = new File(publicDirectory);
        String[] fileNames = file.list();

        for (String fileName : fileNames) {
            body += ("<a href=\"/" + fileName + "\">" + fileName + "</a>\n");
        }
        return body.getBytes();
    }

     byte[] getBodyDefault(Request request) {
        String filePath = publicDirectory + request.getPath();
        return getCorrectPortionOfFileContents(fileIO.getAllBytesFromFile(filePath), request);
    }

     byte[] getCorrectPortionOfFileContents(byte[] fileContents, Request request) {
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

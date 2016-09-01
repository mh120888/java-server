package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

import java.util.Arrays;

public class StaticResourceAction implements Action {
    static String publicDirectory;
    HTTPRequest request;
    FileIO fileIO;

    public StaticResourceAction(String filepath) {
        publicDirectory = filepath;
        fileIO = new RealFileIO();
    }

    public StaticResourceAction(String filepath, FileIO fileInputOutput) {
        publicDirectory = filepath;
        fileIO = fileInputOutput;
    }

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        this.request = request;

        if (isAnyOtherValidRequest()) {
            response.setStatus(HTTPStatus.OK);
            response.setBody(getBody());
        } else {
            response.setStatus(HTTPStatus.METHOD_NOT_ALLOWED);
        }

        return response;
    }

    private boolean isAnyOtherValidRequest() {
        return isAcceptableMethodWithParams(request.getMethod());
    }

    private boolean isAcceptableMethodWithParams(String method) {
        String[] acceptableMethodsWithParams = {"POST", "PUT", "DELETE"};

        return isAcceptableMethod(method, acceptableMethodsWithParams) && !request.getBody().isEmpty();
    }

    private boolean isAcceptableMethod(String method, String[] listOfMethods) {
        return Arrays.asList(listOfMethods).contains(method);
    }

    byte[] getBody() {
        if (isPathADirectory()) {
            return getBodyForDirectory();
        } else {
            return getBodyDefault();
        }
    }

    byte[] getBodyForDirectory() {
        String body = "";
        String[] filenames = fileIO.getFilenames(publicDirectory);
        for (String fileName : filenames) {
            body += ("<a href=\"/" + fileName + "\">" + fileName + "</a>\n");
        }
        return body.getBytes();
    }

     byte[] getBodyDefault() {
         String filePath = publicDirectory + request.getPath();
         return fileIO.getAllBytesFromFile(filePath);
     }

     byte[] getCorrectPortionOfFileContents(byte[] fileContents, int[] range) {
         fileContents = Arrays.copyOfRange(fileContents, range[0], range[1] + 1);
         return fileContents;
     }

     boolean isPathADirectory() {
         String filePath = publicDirectory + request.getPath();
         return fileIO.isDirectory(filePath);
     }
}

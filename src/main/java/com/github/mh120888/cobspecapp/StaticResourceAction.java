package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

import java.util.Arrays;

public class StaticResourceAction implements Action {
    private static String publicDirectory;
    private HTTPRequest request;
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

        if (isPartialContentRequest()) {
            response.setStatus(206);
            setBodyAndHeadersForPartialContentRequest(response);
        } else if (isValidPatchRequest()) {
            response.setStatus(204);
            overwriteFile();
        } else if (isAnyOtherValidRequest()) {
            response.setStatus(HTTPStatus.OK);
            response.setBody(getBody());
        } else {
            response.setStatus(405);
        }

        return response;
    }

    private void setBodyAndHeadersForPartialContentRequest(HTTPResponse response) {
        byte[] fullBodyContents = getBody();
        int[] range = request.getHeaderParser().parseRangeHeader(request.getHeader(HTTPHeaders.RANGE), fullBodyContents);
        response.addHeader(HTTPHeaders.CONTENT_RANGE, "bytes " + range[0] + "-" + range[1] + "/" + fullBodyContents.length);
        response.setBody(getCorrectPortionOfFileContents(fullBodyContents, range));
    }

    private boolean isPartialContentRequest() {
        return request.containsHeader(HTTPHeaders.RANGE);
    }

    private boolean isValidPatchRequest() {
        return request.getMethod().equals("PATCH") && !request.getBody().isEmpty() && request.containsHeader(HTTPHeaders.IF_MATCH);
    }

    private boolean isAnyOtherValidRequest() {
        return isAcceptableMethodWithoutParams(request.getMethod()) || isAcceptableMethodWithParams(request.getMethod());
    }

    private void overwriteFile() {
        fileIO.overwriteFile(publicDirectory + request.getPath(), request.getBody().getBytes());
    }

    private boolean isAcceptableMethodWithoutParams(String method) {
        String[] acceptableMethodsWithoutParams = {"GET", "HEAD"};

        return isAcceptableMethod(method, acceptableMethodsWithoutParams);
    }

    private boolean isAcceptableMethodWithParams(String method) {
        String[] acceptableMethodsWithParams = {"POST", "PUT", "DELETE"};

        return isAcceptableMethod(method, acceptableMethodsWithParams) && !request.getBody().isEmpty();
    }

    private boolean isAcceptableMethod(String method, String[] listOfMethods) {
        return Arrays.asList(listOfMethods).contains(method);
    }

    private byte[] getBody() {
        if (!request.getMethod().equals("GET")) {
            return new byte[0];
        } else if (isPathADirectory()) {
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

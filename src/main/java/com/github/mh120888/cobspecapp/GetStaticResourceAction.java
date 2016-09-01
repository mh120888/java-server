package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

import java.util.Arrays;

public class GetStaticResourceAction implements Action {
    static String publicDirectory;
    HTTPRequest request;
    FileIO fileIO;

    public GetStaticResourceAction(String filepath) {
        publicDirectory = filepath;
        fileIO = new RealFileIO();
    }

    public GetStaticResourceAction(String filepath, FileIO fileInputOutput) {
        publicDirectory = filepath;
        fileIO = fileInputOutput;
    }

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        this.request = request;

        if (isPartialContentRequest()) {
            response.setStatus(HTTPStatus.PARTIAL_CONTENT);
            setBodyAndHeadersForPartialContentRequest(response);
        } else {
            response.setStatus(HTTPStatus.OK);
            response.setBody(getBody());
        }

        return response;
    }

    private boolean isPartialContentRequest() {
        return request.containsHeader(HTTPHeaders.RANGE);
    }

    private void setBodyAndHeadersForPartialContentRequest(HTTPResponse response) {
        byte[] fullBodyContents = getBody();
        int[] range = request.getHeaderParser().parseRangeHeader(request.getHeader(HTTPHeaders.RANGE), fullBodyContents);
        response.addHeader(HTTPHeaders.CONTENT_RANGE, "bytes " + range[0] + "-" + range[1] + "/" + fullBodyContents.length);
        response.setBody(getCorrectPortionOfFileContents(fullBodyContents, range));
    }

    private byte[] getBody() {
        if (isPathADirectory()) {
            return getBodyForDirectory();
        } else {
            return getBodyDefault();
        }
    }

    private byte[] getBodyForDirectory() {
        String body = "";
        String[] filenames = fileIO.getFilenames(publicDirectory);
        for (String fileName : filenames) {
            body += ("<a href=\"/" + fileName + "\">" + fileName + "</a>\n");
        }
        return body.getBytes();
    }

    private byte[] getBodyDefault() {
        String filePath = publicDirectory + request.getPath();
        return fileIO.getAllBytesFromFile(filePath);
    }

    private byte[] getCorrectPortionOfFileContents(byte[] fileContents, int[] range) {
        fileContents = Arrays.copyOfRange(fileContents, range[0], range[1] + 1);
        return fileContents;
    }

    private boolean isPathADirectory() {
        String filePath = publicDirectory + request.getPath();
        return fileIO.isDirectory(filePath);
    }
}

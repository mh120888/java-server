package com.github.mh120888.cobspecapp;

import com.github.mh120888.app.Application;
import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

import java.util.Arrays;

public class GetStaticResourceAction implements Application {
    private static String publicDirectory;
    private HTTPRequest request;
    private FileIO fileIO;

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

        byte[] fullBodyContents = getBody();

        if (isPartialContentRequest()) {
            int[] range = request.getHeaderParser().parseRangeHeader(request.getHeader(HTTPHeaders.RANGE), fullBodyContents);

            response.setStatus(HTTPStatus.PARTIAL_CONTENT);
            response.setBody(getCorrectPortionOfFileContents(fullBodyContents, range));
            response.addHeader(HTTPHeaders.CONTENT_RANGE, getRangeHeaderValue(fullBodyContents));
        } else {
            response.setStatus(HTTPStatus.OK);
            response.setBody(fullBodyContents);
        }

        return response;
    }

    private boolean isPartialContentRequest() {
        return request.containsHeader(HTTPHeaders.RANGE);
    }

    private String getRangeHeaderValue(byte[] fullBodyContents) {
        int[] range = request.getHeaderParser().parseRangeHeader(request.getHeader(HTTPHeaders.RANGE), fullBodyContents);
        return "bytes " + range[0] + "-" + range[1] + "/" + fullBodyContents.length;
    }

    private byte[] getBody() {
        if (isPathADirectory()) {
            return getBodyForDirectory();
        } else {
            return getBodyForFile();
        }
    }

    private byte[] getBodyForDirectory() {
        String body = "";
        String[] allFileNames = fileIO.getFilenames(publicDirectory);
        for (String fileName : allFileNames) {
            body += renderHTMLLinkForFile(fileName);
        }
        return body.getBytes();
    }

    private String renderHTMLLinkForFile(String fileName) {
        return "<a href=\"/" + fileName + "\">" + fileName + "</a>\n";
    }

    private byte[] getBodyForFile() {
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


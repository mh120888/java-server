package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

public class PatchStaticResourceAction implements Action {
    FileIO fileIO;
    String publicDirectory;
    HTTPRequest request;
    HTTPResponse response;

    public PatchStaticResourceAction(String publicDirectory, FileIO fileIO) {
        this.publicDirectory = publicDirectory;
        this.fileIO = fileIO;
    }

    public HTTPResponse getResponse(HTTPRequest request, HTTPResponse response) {
        this.request = request;
        this.response = response;

        if (isValidPatchRequest()) {
            response.setStatus(HTTPStatus.NO_CONTENT);
            overwriteFile();
        } else {
            response.setStatus(HTTPStatus.UNPROCESSABLE_ENTITY);
        }

        return response;
    }

    private boolean isValidPatchRequest() {
        return !request.getBody().isEmpty() && request.containsHeader(HTTPHeaders.IF_MATCH);
    }

    private void overwriteFile() {
        fileIO.overwriteFile(publicDirectory + request.getPath(), request.getBody().getBytes());
    }
}

package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

public class GetStatusResourceAction extends StaticResourceAction {
    public GetStatusResourceAction(String filepath) {
        super(filepath);
    }

    public GetStatusResourceAction(String filepath, FileIO fileInputOutput) {
        super (filepath, fileInputOutput);
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

    private byte[] getBody() {
        if (isPathADirectory()) {
            return getBodyForDirectory();
        } else {
            return getBodyDefault();
        }
    }

    private void setBodyAndHeadersForPartialContentRequest(HTTPResponse response) {
        byte[] fullBodyContents = getBody();
        int[] range = request.getHeaderParser().parseRangeHeader(request.getHeader(HTTPHeaders.RANGE), fullBodyContents);
        response.addHeader(HTTPHeaders.CONTENT_RANGE, "bytes " + range[0] + "-" + range[1] + "/" + fullBodyContents.length);
        response.setBody(getCorrectPortionOfFileContents(fullBodyContents, range));
    }

    boolean isPathADirectory() {
        String filePath = publicDirectory + request.getPath();
        return fileIO.isDirectory(filePath);
    }
}

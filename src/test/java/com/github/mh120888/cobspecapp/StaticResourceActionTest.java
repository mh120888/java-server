package com.github.mh120888.cobspecapp;

import com.github.mh120888.basichttpmessage.BasicHTTPMessageFactory;
import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;
import com.github.mh120888.mocks.MockFileIO;
import com.github.mh120888.mocks.MockHTTPRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class StaticResourceActionTest {
    public static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";
    StaticResourceAction action;

    @Before
    public void setUp() throws Exception {
        action = new StaticResourceAction(publicDirectory);
    }

    @Test
    public void getResponseDataReturnsCorrectResponseLineForGet() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.OK)));
    }

    @Test
    public void getResponseReturns200ForHeadRequest() {
        HTTPResponse response = ResponseGenerator.generateResponse("HEAD",  "/", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.OK)));
    }

    @Test
    public void getResponseReturns405ForInvalidMethods() {
        HTTPResponse response = ResponseGenerator.generateResponse("NOTAREALMETHOD", "/", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.METHOD_NOT_ALLOWED)));
    }

    @Test
    public void getIndexResponseIncludesLinksToOtherResourcesInPublic() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/", action);

        assertTrue(response.getFormattedResponse().contains("<a href=\"/file1\">file1</a>"));
    }

    @Test
    public void postRequestWithNoParamsReturnsA405() {
        HTTPResponse response = ResponseGenerator.generateResponse("POST", "/", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.METHOD_NOT_ALLOWED)));
    }

    @Test
    public void getRequestForAnExistingFileReturnsTheContentsOfThatFile() throws IOException {
        MockFileIO fakeFileIO = new MockFileIO("Fake contents");
        fakeFileIO.respondToIsDirectoryWith(false);
        StaticResourceAction fakeAction = new StaticResourceAction(publicDirectory, fakeFileIO);
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/fake-image.png");

        HTTPResponse response = fakeAction.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        assertTrue(response.getFormattedResponse().contains("Fake contents"));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsA206Response() {
        MockFileIO fakeFileIO = new MockFileIO("Fake contents");
        fakeFileIO.respondToIsDirectoryWith(false);
        StaticResourceAction action = new StaticResourceAction(publicDirectory, fakeFileIO);
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/fake-image.png");
        request.addHeader(HTTPHeaders.RANGE, "bytes=0-4");

        HTTPResponse response = action.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.PARTIAL_CONTENT)));
    }

    @Test
    public void getCorrectPortionOfFileContentsReturnsTheCorrectPortionOfFile() {
        byte[] fullContents = "All of the contents".getBytes();
        int[] range = {0, 5};

        Assert.assertEquals("All of", new String(action.getCorrectPortionOfFileContents(fullContents, range)));
    }
}
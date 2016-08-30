package com.github.mh120888.cobspecapp;

import com.github.mh120888.basichttpmessage.BasicHTTPMessageFactory;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.mocks.MockHTTPRequest;
import com.github.mh120888.mocks.MockFileIO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
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

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturns200ForHeadRequest() {
        HTTPResponse response = ResponseGenerator.generateResponse("HEAD",  "/", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturns405ForInvalidMethods() {
        HTTPResponse response = ResponseGenerator.generateResponse("NOTAREALMETHOD", "/", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 405"));
    }

    @Test
    public void getIndexResponseIncludesLinksToOtherResourcesInPublic() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/", action);

        assertTrue(response.getFormattedResponse().contains("<a href=\"/file1\">file1</a>"));
    }

    @Test
    public void postRequestWithNoParamsReturnsA405() {
        HTTPResponse response = ResponseGenerator.generateResponse("POST", "/", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 405"));
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

        assertEquals(true, response.getFormattedResponse().contains("Fake contents"));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsA206Response() {
        MockFileIO fakeFileIO = new MockFileIO("Fake contents");
        fakeFileIO.respondToIsDirectoryWith(false);
        StaticResourceAction fakeAction = new StaticResourceAction(publicDirectory, fakeFileIO);
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/fake-image.png");
        request.addHeader("Range", "bytes=0-4");

        HTTPResponse response = fakeAction.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        assertTrue(response.getFormattedResponse().contains("206 Partial Content"));
    }

    @Test
    public void getCorrectPortionOfFileContentsReturnsTheCorrectPortionOfFile() {
        byte[] fullContents = "All of the contents".getBytes();
        int[] range = {0, 5};

        Assert.assertEquals("All of", new String(action.getCorrectPortionOfFileContents(fullContents, range)));
    }

    @Test
    public void patchRequestWithContentReturnsA204() {
        StaticResourceAction action = new StaticResourceAction(publicDirectory, new MockFileIO("default content"));
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("PATCH");
        request.setPathWithParams("/partial_content.txt");
        request.addHeader("If-Match", "somethingGoesHere");
        request.setBody("some random content");
        HTTPResponse response = new BasicHTTPMessageFactory().getNewResponse();

        action.getResponse(request, response);

        Assert.assertTrue(response.getFormattedResponse().contains("204 No Content"));
    }

    @Test
    public void modifyResourceWillNotOverwriteContentsOfSpecifiedResourceWithoutIfMatchHeader() throws IOException {
        StaticResourceAction action = new StaticResourceAction(publicDirectory, new MockFileIO("default content"));
        String path = publicDirectory + "/does-not-matter.txt";
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("PATCH");
        request.setPathWithParams("/does-not-matter.txt");
        request.setBody("some random content");

        action.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        String fileContentsAfterPatchRequest = new String(action.fileIO.getAllBytesFromFile(path));
        Assert.assertEquals("default content", fileContentsAfterPatchRequest);
    }

    @Test
    public void modifyResourceWillOverwriteContentsOfSpecifiedResource() throws IOException {
        StaticResourceAction action = new StaticResourceAction(publicDirectory, new MockFileIO("default content"));
        String path = publicDirectory + "/does-not-matter.txt";
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("PATCH");
        request.setPathWithParams("/does-not-matter.txt");
        request.setBody("some random content");
        request.addHeader("If-Match", "somethingGoesHere");

        action.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        String fileContentsAfterPatchRequest = new String(action.fileIO.getAllBytesFromFile(path));
        Assert.assertEquals("some random content", fileContentsAfterPatchRequest);
    }
}
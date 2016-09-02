package com.github.mh120888.cobspecapp;

import com.github.mh120888.basichttpmessage.BasicHTTPMessageFactory;
import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;
import com.github.mh120888.mocks.MockFileIO;
import com.github.mh120888.mocks.MockHTTPRequest;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PatchStaticResourceActionTest {
    public static String publicDirectory = "/something-fake";
    PatchStaticResourceAction action;

    @Test
    public void patchRequestWithContentReturnsA204() {
        PatchStaticResourceAction action = new PatchStaticResourceAction(publicDirectory, new MockFileIO("default content"));
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("PATCH");
        request.setPathWithParams("/partial_content.txt");
        request.addHeader(HTTPHeaders.IF_MATCH, "somethingGoesHere");
        request.setBody("some random content");
        HTTPResponse response = new BasicHTTPMessageFactory().getNewResponse();

        action.getResponse(request, response);

        Assert.assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.NO_CONTENT)));
    }

    @Test
    public void modifyResourceWillNotOverwriteContentsOfSpecifiedResourceWithoutIfMatchHeader() throws IOException {
        PatchStaticResourceAction action = new PatchStaticResourceAction(publicDirectory, new MockFileIO("default content"));
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("PATCH");
        request.setPathWithParams("/does-not-matter.txt");
        request.setBody("some random content");

        action.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        String path = publicDirectory + "/does-not-matter.txt";
        String fileContentsAfterPatchRequest = new String(action.fileIO.getAllBytesFromFile(path));
        Assert.assertEquals("default content", fileContentsAfterPatchRequest);
    }

    @Test
    public void modifyResourceWillOverwriteContentsOfSpecifiedResource() throws IOException {
        PatchStaticResourceAction action = new PatchStaticResourceAction(publicDirectory, new MockFileIO("default content"));
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("PATCH");
        request.setPathWithParams("/does-not-matter.txt");
        request.setBody("some random content");
        request.addHeader(HTTPHeaders.IF_MATCH, "somethingGoesHere");

        action.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        String path = publicDirectory + "/does-not-matter.txt";
        String fileContentsAfterPatchRequest = new String(action.fileIO.getAllBytesFromFile(path));
        Assert.assertEquals("some random content", fileContentsAfterPatchRequest);
    }
}
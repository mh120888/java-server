package com.github.mh120888.cobspecapp;

import com.github.mh120888.basichttpmessage.BasicHTTPMessageFactory;
import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;
import com.github.mh120888.mocks.MockFileIO;
import com.github.mh120888.mocks.MockHTTPRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GetStatusResourceActionTest {
    public static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";
    GetStatusResourceAction action;

    @Before
    public void setUp() throws Exception {
        action = new GetStatusResourceAction(publicDirectory);
    }

    @Test
    public void getResponseDataReturnsCorrectResponseLineForGet() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/", action);

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.OK)));
    }

    @Test
    public void getIndexResponseIncludesLinksToOtherResourcesInPublic() {
        HTTPResponse response = ResponseGenerator.generateResponse("GET", "/", action);

        assertTrue(response.getFormattedResponse().contains("<a href=\"/file1\">file1</a>"));
    }

    @Test
    public void getRequestForAnExistingFileReturnsTheContentsOfThatFile() throws IOException {
        MockFileIO fakeFileIO = new MockFileIO("Fake contents");
        fakeFileIO.respondToIsDirectoryWith(false);
        GetStatusResourceAction action = new GetStatusResourceAction(publicDirectory, fakeFileIO);
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/fake-image.png");

        HTTPResponse response = action.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        assertTrue(response.getFormattedResponse().contains("Fake contents"));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsA206Response() {
        MockFileIO fakeFileIO = new MockFileIO("Fake contents");
        fakeFileIO.respondToIsDirectoryWith(false);
        GetStatusResourceAction action = new GetStatusResourceAction(publicDirectory, fakeFileIO);
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/fake-image.png");
        request.addHeader(HTTPHeaders.RANGE, "bytes=0-4");

        HTTPResponse response = action.getResponse(request, new BasicHTTPMessageFactory().getNewResponse());

        assertTrue(response.getFormattedResponse().contains(Integer.toString(HTTPStatus.PARTIAL_CONTENT)));
    }
}
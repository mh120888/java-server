package cobspecapp;

import mocks.MockHTTPRequest;
import response.Response;
import httpresponse.HTTPResponse;
import mocks.MockFileIO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class StaticResourceActionTest {
    public static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";
    StaticResourceAction action;

    @Before
    public void setUp() throws Exception {
        action = new StaticResourceAction(publicDirectory);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getResponseDataReturnsCorrectResponseLineForGet() {
        Response response = ResponseGenerator.generateResponse("GET", "/", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturns200ForHeadRequest() {
        Response response = ResponseGenerator.generateResponse("HEAD",  "/", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturns405ForInvalidMethods() {
        Response response = ResponseGenerator.generateResponse("NOTAREALMETHOD", "/", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 405"));
    }

    @Test
    public void getIndexResponseIncludesLinksToOtherResourcesInPublic() {
        Response response = ResponseGenerator.generateResponse("GET", "/", action);

        assertTrue(response.getFormattedResponse().contains("<a href=\"/file1\">file1</a>"));
    }

    @Test
    public void postRequestWithNoParamsReturnsA405() {
        Response response = ResponseGenerator.generateResponse("POST", "/", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 405"));
    }

    @Test
    public void isPathADirectoryReturnsTrueForADirectory() {
        Response response = ResponseGenerator.generateResponse("GET", "/", action);

        Assert.assertEquals(true, action.isPathADirectory());
    }

    @Test
    public void isPathADirectoryReturnsFalseForAFile() {
        Response response = ResponseGenerator.generateResponse("GET", "/file1", action);

        Assert.assertEquals(false, action.isPathADirectory());
    }

    @Test
    public void getRequestForAnExistingFileReturnsTheContentsOfThatFile() throws IOException {
        String path = publicDirectory + "/image.png";
        byte[] imageContents = Files.readAllBytes(Paths.get(path));

        Response response = ResponseGenerator.generateResponse("GET", "/image.png", action);

        assertEquals(true, response.getFormattedResponse().contains(new String(imageContents)));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsA206Response() {
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/partial_content.txt");
        request.addHeader("Range", "bytes=0-10");
        Response response = action.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("206 Partial Content"));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsOnlyTheSpecifiedRangeOfTheRequestedResource() throws IOException {
        String path = publicDirectory + "/partial_content.txt";
        byte[] fullFileContents = Files.readAllBytes(Paths.get(path));
        byte[] requestedFileContents = Arrays.copyOfRange(fullFileContents, 0, 5);
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/partial_content.txt");
        request.addHeader("Range", "bytes=0-4");

        Response response = action.getResponse(request, new HTTPResponse());

        assertTrue(Arrays.equals(requestedFileContents, response.getBody()));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsCorrectPortionOfResourceWhenNoStartIsGiven() throws IOException {
        String path = publicDirectory + "/partial_content.txt";
        byte[] fullFileContents = Files.readAllBytes(Paths.get(path));
        byte[] requestedFileContents = Arrays.copyOfRange(fullFileContents, 71, fullFileContents.length);

        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/partial_content.txt");
        request.addHeader("Range", "bytes=-6");
        Response response = action.getResponse(request, new HTTPResponse());

        assertTrue(Arrays.equals(requestedFileContents, response.getBody()));
    }

    @Test
    public void requestForPartialContentsReturnsResponseWithContentRangeHeader() {
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/partial_content.txt");
        request.addHeader("Range", "bytes=0-4");
        Response response = action.getResponse(request, new HTTPResponse());

        assertTrue(response.getStatusLineAndHeaders().contains("Content-Range: bytes 0-4/77"));
    }


    @Test
    public void getRequestWithRangeHeadersReturnsCorrectPortionOfResourceWhenNoEndIsGiven() throws IOException {
        String path = publicDirectory + "/partial_content.txt";
        byte[] fullFileContents = Files.readAllBytes(Paths.get(path));
        byte[] requestedFileContents = Arrays.copyOfRange(fullFileContents, 4, fullFileContents.length);
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("GET");
        request.setPathWithParams("/partial_content.txt");
        request.addHeader("Range", "bytes=4-");

        Response response = action.getResponse(request, new HTTPResponse());

        assertTrue(Arrays.equals(response.getBody(), requestedFileContents));
    }

    @Test
    public void patchRequestWithContentReturnsA204() {
        StaticResourceAction action = StaticResourceAction.getStaticResourceActionWithFileIO(publicDirectory, new MockFileIO("default content"));
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("PATCH");
        request.setPathWithParams("/partial_content.txt");
        request.addHeader("If-Match", "somethingGoesHere");
        request.setBody("some random content");
        Response response = new HTTPResponse();

        action.getResponse(request, response);

        Assert.assertTrue(response.getFormattedResponse().contains("204 No Content"));
    }

    @Test
    public void modifyResourceWillNotOverwriteContentsOfSpecifiedResourceWithoutIfMatchHeader() throws IOException {
        StaticResourceAction action = StaticResourceAction.getStaticResourceActionWithFileIO(publicDirectory, new MockFileIO("default content"));
        String path = publicDirectory + "/does-not-matter.txt";
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("PATCH");
        request.setPathWithParams("/does-not-matter.txt");
        request.setBody("some random content");

        action.getResponse(request, new HTTPResponse());

        String fileContentsAfterPatchRequest = new String(action.fileIO.getAllBytesFromFile(path));
        Assert.assertEquals("default content", fileContentsAfterPatchRequest);
    }

    @Test
    public void modifyResourceWillOverwriteContentsOfSpecifiedResource() throws IOException {
        StaticResourceAction action = StaticResourceAction.getStaticResourceActionWithFileIO(publicDirectory, new MockFileIO("default content"));
        String path = publicDirectory + "/does-not-matter.txt";
        MockHTTPRequest request = new MockHTTPRequest();
        request.setMethod("PATCH");
        request.setPathWithParams("/does-not-matter.txt");
        request.setBody("some random content");
        request.addHeader("If-Match", "somethingGoesHere");

        action.getResponse(request, new HTTPResponse());

        String fileContentsAfterPatchRequest = new String(action.fileIO.getAllBytesFromFile(path));
        Assert.assertEquals("some random content", fileContentsAfterPatchRequest);
    }
}
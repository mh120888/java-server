package cobspecapp;

import abstracthttpresponse.AbstractHTTPResponse;
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
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET / HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturns200ForHeadRequest() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("HEAD / HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturns405ForInvalidMethods() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("NOTAREALMETHOD / HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 405"));
    }

    @Test
    public void getIndexResponseIncludesLinksToOtherResourcesInPublic() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET / HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("<a href=\"/file1\">file1</a>"));
    }

    @Test
    public void postRequestWithNoParamsReturnsA405() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("POST / HTTP/1.1", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 405"));
    }

    @Test
    public void isPathADirectoryReturnsTrueForADirectory() {
        String path = "/";

        Assert.assertEquals(true, action.isPathADirectory(path));
    }

    @Test
    public void isPathADirectoryReturnsFalseForAFile() {
        String path = "/file1";

        Assert.assertEquals(false, action.isPathADirectory(path));
    }

    @Test
    public void getFiletypeReturnsDIRECTORYForADirectory() {
        String path = "/";

        Assert.assertEquals(StaticResourceAction.Filetype.DIRECTORY, action.getFiletype(path));
    }

    @Test
    public void getFiletypeReturnsIMAGEForAnImage() {
        String path = "/image.png";

        Assert.assertEquals(StaticResourceAction.Filetype.IMAGE, action.getFiletype(path));
    }

    @Test
    public void getFiletypeReturnsOTHERForATextFile() {
        String path = "/text-file.txt";

        Assert.assertEquals(StaticResourceAction.Filetype.OTHER, action.getFiletype(path));
    }

    @Test
    public void getRequestForAnExistingFileReturnsTheContentsOfThatFile() throws IOException {
        String path = publicDirectory + "/image.png";
        byte[] imageContents = Files.readAllBytes(Paths.get(path));

        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /image.png HTTP/1.1", action);

        assertEquals(true, response.getFormattedResponse().contains(new String(imageContents)));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsA206Response() {
        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /partial_content.txt HTTP/1.1\nRange: bytes=0-10", action);

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 206 Partial Content"));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsOnlyTheSpecifiedRangeOfTheRequestedResource() throws IOException {
        String path = publicDirectory + "/partial_content.txt";
        byte[] fullFileContents = Files.readAllBytes(Paths.get(path));
        byte[] requestedFileContents = Arrays.copyOfRange(fullFileContents, 0, 5);

        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /partial_content.txt HTTP/1.1\nRange: bytes=0-4", action);

        assertTrue(Arrays.equals(requestedFileContents, response.getBody()));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsCorrectPortionOfResourceWhenNoStartIsGiven() throws IOException {
        String path = publicDirectory + "/partial_content.txt";
        byte[] fullFileContents = Files.readAllBytes(Paths.get(path));
        byte[] requestedFileContents = Arrays.copyOfRange(fullFileContents, 71, fullFileContents.length);

        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /partial_content.txt HTTP/1.1\nRange: bytes=-6", action);

        assertTrue(Arrays.equals(requestedFileContents, response.getBody()));
    }


    @Test
    public void getRequestWithRangeHeadersReturnsCorrectPortionOfResourceWhenNoEndIsGiven() throws IOException {
        String path = publicDirectory + "/partial_content.txt";
        byte[] fullFileContents = Files.readAllBytes(Paths.get(path));
        byte[] requestedFileContents = Arrays.copyOfRange(fullFileContents, 4, fullFileContents.length);

        AbstractHTTPResponse response = ResponseGenerator.generateResponse("GET /partial_content.txt HTTP/1.1\nRange: bytes=4-", action);

        assertTrue(Arrays.equals(response.getBody(), requestedFileContents));
    }
}
package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;
import httprequest.HTTPRequest;
import httpresponse.HTTPResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class StaticResourceActionTest {
    public static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getResponseDataReturnsCorrectResponseLineForGet() {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);

        AbstractHTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturns200ForHeadRequest() {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);
        AbstractHTTPRequest request = new HTTPRequest("HEAD / HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturns405ForInvalidMethods() {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);
        AbstractHTTPRequest request = new HTTPRequest("NOTAREALMETHOD / HTTP/1.1");

        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 405"));

    }

    @Test
    public void getIndexResponseIncludesLinksToOtherResourcesInPublic() {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);
        AbstractHTTPRequest request = new HTTPRequest("GET / HTTP/1.1");

        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("<a href=\"/file1\">file1</a>"));

    }

    @Test
    public void postRequestWithNoParamsReturnsA405() {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);
        AbstractHTTPRequest request = new HTTPRequest("POST / HTTP/1.1");

        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 405"));
    }
//
//    @Test
//    public void postRequestWithParamsReturnsA200() {
//        cobspecapp.StaticResourceAction endpoint = new cobspecapp.StaticResourceAction();
//        HashMap<String, String> request = server.HTTPRequestParser.parse("POST / HTTP/1.0\n\nsomeParam=something");
//        HashMap<String, String> response = endpoint.getResponse(request);
//
//        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
//    }
//
//    @Test
//    public void putRequestWithParamsReturnsA200() {
//        cobspecapp.StaticResourceAction endpoint = new cobspecapp.StaticResourceAction();
//        HashMap<String, String> request = server.HTTPRequestParser.parse("PUT / HTTP/1.1\n\nsomeParam=something");
//        HashMap<String, String> response = endpoint.getResponse(request);
//
//        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
//    }

    @Test
    public void isPathADirectoryReturnsTrueForADirectory() {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);
        String path = "/";

        Assert.assertEquals(true, endpoint.isPathADirectory(path));
    }

    @Test
    public void isPathADirectoryReturnsFalseForAFile() {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);
        String path = "/file1";

        Assert.assertEquals(false, endpoint.isPathADirectory(path));
    }

    @Test
    public void getFiletypeReturnsDIRECTORYForADirectory() {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);
        String path = "/";

        Assert.assertEquals(StaticResourceAction.Filetype.DIRECTORY, endpoint.getFiletype(path));
    }

    @Test
    public void getFiletypeReturnsIMAGEForAnImage() {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);
        String path = "/image.png";

        Assert.assertEquals(StaticResourceAction.Filetype.IMAGE, endpoint.getFiletype(path));
    }

    @Test
    public void getFiletypeReturnsOTHERForATextFile() {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);
        String path = "/text-file.txt";

        Assert.assertEquals(StaticResourceAction.Filetype.OTHER, endpoint.getFiletype(path));
    }

    @Test
    public void getRequestForAnExistingFileReturnsTheContentsOfThatFile() throws IOException {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);
        String path = publicDirectory + "/image.png";
        byte[] imageContents = Files.readAllBytes(Paths.get(path));
        AbstractHTTPRequest request = new HTTPRequest("GET /image.png HTTP/1.1");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertEquals(true, response.getFormattedResponse().contains(new String(imageContents)));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsA206Response() {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);

        AbstractHTTPRequest request = new HTTPRequest("GET /partial_content.txt HTTP/1.1\nRange: bytes=0-10");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.getFormattedResponse().contains("HTTP/1.1 206 Partial Content"));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsOnlyTheSpecifiedRangeOfTheRequestedResource() throws IOException {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);
        String path = publicDirectory + "/partial_content.txt";
        byte[] fullFileContents = Files.readAllBytes(Paths.get(path));
        byte[] requestedFileContents = Arrays.copyOfRange(fullFileContents, 0, 5);

        AbstractHTTPRequest request = new HTTPRequest("GET /partial_content.txt HTTP/1.1\nRange: bytes=0-4");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(Arrays.equals(requestedFileContents, response.getBody()));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsCorrectPortionOfResourceWhenNoStartIsGiven() throws IOException {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);
        String path = publicDirectory + "/partial_content.txt";
        byte[] fullFileContents = Files.readAllBytes(Paths.get(path));
        byte[] requestedFileContents = Arrays.copyOfRange(fullFileContents, 71, fullFileContents.length);

        AbstractHTTPRequest request = new HTTPRequest("GET /partial_content.txt HTTP/1.1\nRange: bytes=-6");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(Arrays.equals(requestedFileContents, response.getBody()));
    }


    @Test
    public void getRequestWithRangeHeadersReturnsCorrectPortionOfResourceWhenNoEndIsGiven() throws IOException {
        StaticResourceAction endpoint = new StaticResourceAction(publicDirectory);
        String path = publicDirectory + "/partial_content.txt";
        byte[] fullFileContents = Files.readAllBytes(Paths.get(path));
        byte[] requestedFileContents = Arrays.copyOfRange(fullFileContents, 4, fullFileContents.length);

        AbstractHTTPRequest request = new HTTPRequest("GET /partial_content.txt HTTP/1.1\nRange: bytes=4-");
        AbstractHTTPResponse response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(Arrays.equals(response.getBody(), requestedFileContents));
    }
}
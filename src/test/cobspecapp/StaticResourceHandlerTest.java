package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
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
public class StaticResourceHandlerTest {
    public static String publicDirectory = "/Users/matthewhiggins/Desktop/cob_spec/public";

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getResponseDataReturnsCorrectResponseLineForGet() {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);

        AbstractHTTPRequest request = new HTTPRequest("GET / HTTP/1.1");
        String response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturns200ForHeadRequest() {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);
        AbstractHTTPRequest request = new HTTPRequest("HEAD / HTTP/1.1");
        String response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.contains("HTTP/1.1 200"));
    }

    @Test
    public void getResponseReturns405ForInvalidMethods() {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);
        AbstractHTTPRequest request = new HTTPRequest("NOTAREALMETHOD / HTTP/1.1");

        String response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.contains("HTTP/1.1 405"));

    }

    @Test
    public void getIndexResponseIncludesLinksToOtherResourcesInPublic() {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);
        AbstractHTTPRequest request = new HTTPRequest("GET / HTTP/1.1");

        String response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.contains("<a href=\"/file1\">file1</a>"));

    }

    @Test
    public void postRequestWithNoParamsReturnsA405() {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);
        AbstractHTTPRequest request = new HTTPRequest("POST / HTTP/1.1");

        String response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.contains("HTTP/1.1 405"));
    }
//
//    @Test
//    public void postRequestWithParamsReturnsA200() {
//        cobspecapp.StaticResourceHandler endpoint = new cobspecapp.StaticResourceHandler();
//        HashMap<String, String> request = server.HTTPRequestParser.parse("POST / HTTP/1.0\n\nsomeParam=something");
//        HashMap<String, String> response = endpoint.getResponse(request);
//
//        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
//    }
//
//    @Test
//    public void putRequestWithParamsReturnsA200() {
//        cobspecapp.StaticResourceHandler endpoint = new cobspecapp.StaticResourceHandler();
//        HashMap<String, String> request = server.HTTPRequestParser.parse("PUT / HTTP/1.1\n\nsomeParam=something");
//        HashMap<String, String> response = endpoint.getResponse(request);
//
//        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
//    }

    @Test
    public void isPathADirectoryReturnsTrueForADirectory() {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);
        String path = "/";

        Assert.assertEquals(true, endpoint.isPathADirectory(path));
    }

    @Test
    public void isPathADirectoryReturnsFalseForAFile() {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);
        String path = "/file1";

        Assert.assertEquals(false, endpoint.isPathADirectory(path));
    }

    @Test
    public void getFiletypeReturnsDIRECTORYForADirectory() {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);
        String path = "/";

        Assert.assertEquals(StaticResourceHandler.Filetype.DIRECTORY, endpoint.getFiletype(path));
    }

    @Test
    public void getFiletypeReturnsIMAGEForAnImage() {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);
        String path = "/image.png";

        Assert.assertEquals(StaticResourceHandler.Filetype.IMAGE, endpoint.getFiletype(path));
    }

    @Test
    public void getFiletypeReturnsOTHERForATextFile() {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);
        String path = "/text-file.txt";

        Assert.assertEquals(StaticResourceHandler.Filetype.OTHER, endpoint.getFiletype(path));
    }

    @Test
    public void getRequestForAnExistingFileReturnsTheContentsOfThatFile() throws IOException {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);
        String path = publicDirectory + "/image.png";
        byte[] imageContents = Files.readAllBytes(Paths.get(path));
        AbstractHTTPRequest request = new HTTPRequest("GET /image.png HTTP/1.1");
        String response = endpoint.getResponse(request, new HTTPResponse());

        assertEquals(true, response.contains(new String(imageContents)));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsA206Response() {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);

        AbstractHTTPRequest request = new HTTPRequest("GET /partial_content.txt HTTP/1.1\nRange: bytes=0-10");
        String response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.contains("HTTP/1.1 206 Partial Content"));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsOnlyTheSpecifiedRangeOfTheRequestedResource() throws IOException {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);
        String path = publicDirectory + "/partial_content.txt";
        byte[] fullFileContents = Files.readAllBytes(Paths.get(path));
        byte[] requestedFileContents = Arrays.copyOfRange(fullFileContents, 0, 11);

        AbstractHTTPRequest request = new HTTPRequest("GET /partial_content.txt HTTP/1.1\nRange: bytes=0-10");
        String response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.contains("This is a f"));
        assertFalse(response.contains(new String(fullFileContents)));
    }

    @Test
    public void getRequestWithRangeHeadersReturnsCorrectPortionOfResourceWhenNoStartIsGiven() throws IOException {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);
        String path = publicDirectory + "/partial_content.txt";
        byte[] fullFileContents = Files.readAllBytes(Paths.get(path));
        byte[] requestedFileContents = Arrays.copyOfRange(fullFileContents, 72, fullFileContents.length);

        AbstractHTTPRequest request = new HTTPRequest("GET /partial_content.txt HTTP/1.1\nRange: bytes=-6");
        String response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.contains(new String(requestedFileContents)));
        assertFalse(response.contains(new String(fullFileContents)));
    }


    @Test
    public void getRequestWithRangeHeadersReturnsCorrectPortionOfResourceWhenNoEndIsGiven() throws IOException {
        StaticResourceHandler endpoint = new StaticResourceHandler(publicDirectory);
        String path = publicDirectory + "/partial_content.txt";
        byte[] fullFileContents = Files.readAllBytes(Paths.get(path));
        byte[] requestedFileContents = Arrays.copyOfRange(fullFileContents, 4, fullFileContents.length);

        AbstractHTTPRequest request = new HTTPRequest("GET /partial_content.txt HTTP/1.1\nRange: bytes=4-");
        String response = endpoint.getResponse(request, new HTTPResponse());

        assertTrue(response.contains(new String(requestedFileContents)));
        assertFalse(response.contains(new String(fullFileContents)));
    }
}
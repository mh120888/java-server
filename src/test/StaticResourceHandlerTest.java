package test;

import cobspecapp.StaticResourceHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.HTTPRequestParser;
import server.MyServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class StaticResourceHandlerTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getReponseDataReturnsCorrectResponseLineForGet() {
        StaticResourceHandler endpoint = new StaticResourceHandler();
        HashMap<String, String> request = HTTPRequestParser.parse("GET / HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

    @Test
    public void getResponseDataReturns200ForHeadRequest() {
        StaticResourceHandler endpoint = new StaticResourceHandler();
        HashMap<String, String> request = HTTPRequestParser.parse("HEAD / HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

    @Test
    public void getResponseDataReturns405ForInvalidMethods() {
        StaticResourceHandler endpoint = new StaticResourceHandler();
        HashMap<String, String> request = HTTPRequestParser.parse("NOTAREALMETHOD / HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 405 Method Not Allowed", response.get("responseLine"));
    }

    @Test
    public void getIndexResponseIncludesLinksToOtherResourcesInPublic() {
        StaticResourceHandler endpoint = new StaticResourceHandler();
        HashMap<String, String> request = HTTPRequestParser.parse("GET / HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertTrue(response.get("body").contains("<a href=\"/file1\">file1</a>"));
    }

    @Test
    public void postRequestWithNoParamsReturnsA405() {
        StaticResourceHandler endpoint = new StaticResourceHandler();
        HashMap<String, String> request = HTTPRequestParser.parse("POST / HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 405 Method Not Allowed", response.get("responseLine"));
    }
//
//    @Test
//    public void postRequestWithParamsReturnsA200() {
//        cobspecapp.StaticResourceHandler endpoint = new cobspecapp.StaticResourceHandler();
//        HashMap<String, String> request = server.HTTPRequestParser.parse("POST / HTTP/1.0\n\nsomeParam=something");
//        HashMap<String, String> response = endpoint.getResponseData(request);
//
//        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
//    }
//
//    @Test
//    public void putRequestWithParamsReturnsA200() {
//        cobspecapp.StaticResourceHandler endpoint = new cobspecapp.StaticResourceHandler();
//        HashMap<String, String> request = server.HTTPRequestParser.parse("PUT / HTTP/1.1\n\nsomeParam=something");
//        HashMap<String, String> response = endpoint.getResponseData(request);
//
//        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
//    }

    @Test
    public void isPathADirectoryReturnsTrueForADirectory() {
        StaticResourceHandler endpoint = new StaticResourceHandler();
        String path = "/";

        Assert.assertEquals(true, endpoint.isPathADirectory(path));
    }

    @Test
    public void isPathADirectoryReturnsFalseForAFile() {
        StaticResourceHandler endpoint = new StaticResourceHandler();
        String path = "/file1";

        Assert.assertEquals(false, endpoint.isPathADirectory(path));
    }

    @Test
    public void getFiletypeReturnsDIRECTORYForADirectory() {
        StaticResourceHandler endpoint = new StaticResourceHandler();
        String path = "/";

        Assert.assertEquals(StaticResourceHandler.Filetype.DIRECTORY, endpoint.getFiletype(path));
    }

    @Test
    public void getFiletypeReturnsIMAGEForAnImage() {
        StaticResourceHandler endpoint = new StaticResourceHandler();
        String path = "/image.png";

        Assert.assertEquals(StaticResourceHandler.Filetype.IMAGE, endpoint.getFiletype(path));
    }

    @Test
    public void getFiletypeReturnsOTHERForATextFile() {
        StaticResourceHandler endpoint = new StaticResourceHandler();
        String path = "/text-file.txt";

        Assert.assertEquals(StaticResourceHandler.Filetype.OTHER, endpoint.getFiletype(path));
    }

    @Test
    public void getRequestForAnExistingFileReturnsTheContentsOfThatFile() throws IOException {
        StaticResourceHandler endpoint = new StaticResourceHandler();
        String path = MyServer.publicDirectory + "/image.png";
        byte[] imageContents = Files.readAllBytes(Paths.get(path));
        HashMap<String, String> request = HTTPRequestParser.parse("GET /image.png HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals(true, response.get("body").contains(new String(imageContents)));
    }
}
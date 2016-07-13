import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class StaticResourceEndpointTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getReponseDataReturnsCorrectResponseLineForGet() {
        StaticResourceEndpoint endpoint = new StaticResourceEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET / HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

    @Test
    public void getResponseDataReturns200ForHeadRequest() {
        StaticResourceEndpoint endpoint = new StaticResourceEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("HEAD / HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
    }

    @Test
    public void getResponseDataReturns405ForInvalidMethods() {
        StaticResourceEndpoint endpoint = new StaticResourceEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("NOTAREALMETHOD / HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 405 Method Not Allowed", response.get("responseLine"));
    }

    @Test
    public void getIndexResponseIncludesLinksToOtherResourcesInPublic() {
        StaticResourceEndpoint endpoint = new StaticResourceEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("GET / HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertTrue(response.get("body").contains("<a href=\"/file1\">file1</a>"));
    }

    @Test
    public void postRequestWithNoParamsReturnsA405() {
        StaticResourceEndpoint endpoint = new StaticResourceEndpoint();
        HashMap<String, String> request = HTTPRequestParser.parse("POST / HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals("HTTP/1.1 405 Method Not Allowed", response.get("responseLine"));
    }
//
//    @Test
//    public void postRequestWithParamsReturnsA200() {
//        StaticResourceEndpoint endpoint = new StaticResourceEndpoint();
//        HashMap<String, String> request = HTTPRequestParser.parse("POST / HTTP/1.0\n\nsomeParam=something");
//        HashMap<String, String> response = endpoint.getResponseData(request);
//
//        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
//    }
//
//    @Test
//    public void putRequestWithParamsReturnsA200() {
//        StaticResourceEndpoint endpoint = new StaticResourceEndpoint();
//        HashMap<String, String> request = HTTPRequestParser.parse("PUT / HTTP/1.1\n\nsomeParam=something");
//        HashMap<String, String> response = endpoint.getResponseData(request);
//
//        assertEquals("HTTP/1.1 200 OK", response.get("responseLine"));
//    }

    @Test
    public void isPathADirectoryReturnsTrueForADirectory() {
        StaticResourceEndpoint endpoint = new StaticResourceEndpoint();
        String path = "/";

        assertEquals(true, endpoint.isPathADirectory(path));
    }

    @Test
    public void isPathADirectoryReturnsFalseForAFile() {
        StaticResourceEndpoint endpoint = new StaticResourceEndpoint();
        String path = "/file1";

        assertEquals(false, endpoint.isPathADirectory(path));
    }

    @Test
    public void getFiletypeReturnsDIRECTORYForADirectory() {
        StaticResourceEndpoint endpoint = new StaticResourceEndpoint();
        String path = "/";

        assertEquals(StaticResourceEndpoint.Filetype.DIRECTORY, endpoint.getFiletype(path));
    }

    @Test
    public void getFiletypeReturnsIMAGEForAnImage() {
        StaticResourceEndpoint endpoint = new StaticResourceEndpoint();
        String path = "/image.png";

        assertEquals(StaticResourceEndpoint.Filetype.IMAGE, endpoint.getFiletype(path));
    }

    @Test
    public void getFiletypeReturnsOTHERForATextFile() {
        StaticResourceEndpoint endpoint = new StaticResourceEndpoint();
        String path = "/text-file.txt";

        assertEquals(StaticResourceEndpoint.Filetype.OTHER, endpoint.getFiletype(path));
    }

    @Test
    public void getRequestForAnExistingFileReturnsTheContentsOfThatFile() throws IOException {
        StaticResourceEndpoint endpoint = new StaticResourceEndpoint();
        String path = Endpoint.FILEPATH + "/image.png";
        byte[] imageContents = Files.readAllBytes(Paths.get(path));
        HashMap<String, String> request = HTTPRequestParser.parse("GET /image.png HTTP/1.1");
        HashMap<String, String> response = endpoint.getResponseData(request);

        assertEquals(true, response.get("body").contains(new String(imageContents)));
    }
}
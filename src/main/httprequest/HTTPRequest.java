package httprequest;

import abstracthttprequest.AbstractHttpRequest;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/20/16.
 */
public class HTTPRequest extends AbstractHttpRequest {

    private String method;
    private String path;
    private String version;
    private HashMap<String, String> headers = new HashMap<>();;

    public HTTPRequest(String requestAsString) {
        String[] breakUpRequest = requestAsString.split("\n");
        String[] splitIRL = breakUpRequest[0].split(" ");
        method = splitIRL[0];
        path = splitIRL[1];
        version = splitIRL[2];

        if (breakUpRequest.length > 1) {
            String[] wholeHeaders = Arrays.copyOfRange(breakUpRequest, 1, breakUpRequest.length);
            for (String headerPair : wholeHeaders) {
                String[] separatePair = headerPair.split(":");
                String headerName = separatePair[0];
                String headerValue = String.join(":", Arrays.copyOfRange(separatePair, 1, separatePair.length)).trim();
                headers.put(headerName, headerValue);
            }
        }
    };

    public String getMethod() {
        return method;
    };

    public String getPath() {
        return path;
    };

    public String getVersion() {
        return version;
    };

    public HashMap<String, String> getAllHeaders() {
        return headers;
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }
}

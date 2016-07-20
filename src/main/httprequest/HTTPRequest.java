package httprequest;

import abstracthttprequest.AbstractHttpRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthewhiggins on 7/20/16.
 */
public class HTTPRequest extends AbstractHttpRequest {

    private String method;
    private String path;
    private String version;
    private HashMap<String, String> headers = new HashMap<>();;
    private HashMap<String, String> params = new HashMap<>();;

    private static Map<String, String> decoderPairs = new HashMap<>();
    static {
        decoderPairs.put("%20", " ");
        decoderPairs.put("%3C", "<");
        decoderPairs.put("%2C", ",");
        decoderPairs.put("%3E", ">");
        decoderPairs.put("%3D", "=");
        decoderPairs.put("%3B", ";");
        decoderPairs.put("%2B", "+");
        decoderPairs.put("%26", "&");
        decoderPairs.put("%40", "@");
        decoderPairs.put("%23", "#");
        decoderPairs.put("%24", "\\$");
        decoderPairs.put("%5B", "[");
        decoderPairs.put("%5D", "]");
        decoderPairs.put("%3A", ":");
        decoderPairs.put("%22", "\"");
        decoderPairs.put("%3F", "\\?");
    }


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

        String[] splitPathFromParams = path.split("\\?");
        if (splitPathFromParams.length > 1) {
            String[] allParams = splitPathFromParams[1].split("&");
            for (String singleParam: allParams) {
                String[] paramValuePair = singleParam.split("=");
                String value = paramValuePair[1];
                for (Map.Entry<String, String> entry : decoderPairs.entrySet()) {
                    value = value.replaceAll(entry.getKey(), entry.getValue());
                }
                params.put(paramValuePair[0], value);
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

    public boolean headerExists(String headerName) {
        return headers.containsKey(headerName);
    }

    public HashMap<String, String> getAllParams() {
        return params;
    }

    public boolean paramExists(String paramName) {
        return params.containsKey(paramName);
    };

    public String getParam(String paramName) {
        return params.get(paramName);
    }

    public String getBaseLocation() {
        return "http://localhost:5000/";
    }
}

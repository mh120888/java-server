package httprequest;

import abstracthttprequest.AbstractHTTPRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthewhiggins on 7/20/16.
 */
public class HTTPRequest extends AbstractHTTPRequest {

    private String method;
    private String pathWithParams;
    private String version;
    private HashMap<String, String> headers = new HashMap<>();;
    private HashMap<String, String> params = new HashMap<>();;

    public HTTPRequest(String requestAsString) {
        String[] requestSeparatedByNewline = requestAsString.split("\n");
        String[] splitIRL = requestSeparatedByNewline[0].split(" ");
        method = splitIRL[0];
        pathWithParams = splitIRL[1];
        version = splitIRL[2];

        setHeaders(requestSeparatedByNewline);
        setParams(pathWithParams.split("\\?"));
    };

    public String getMethod() {
        return method;
    };

    public String getPath() {
        return pathWithParams.split("\\?")[0];
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

    public String getInitialRequestLine() {
        return method + " " + pathWithParams + " " + version;
    }

    public HeaderParser getHeaderParser() {
        return new HeaderParser();
    }

    private void setHeaders(String[] requestSeparatedByNewline) {
        if (requestSeparatedByNewline.length > 1) {
            String[] justTheHeaders = Arrays.copyOfRange(requestSeparatedByNewline, 1, requestSeparatedByNewline.length);
            for (String headerPair : justTheHeaders) {
                String[] separatePair = headerPair.split(":");
                String headerName = separatePair[0];
                String headerValue = String.join(":", Arrays.copyOfRange(separatePair, 1, separatePair.length)).trim();
                headers.put(headerName, headerValue);
            }
        }
    }

    private void setParams(String[] splitPathFromParams) {
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
    }

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
}

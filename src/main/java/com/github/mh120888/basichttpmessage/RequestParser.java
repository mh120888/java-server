package com.github.mh120888.basichttpmessage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.github.mh120888.httpmessage.MessageFormatting.CRLF;

public class RequestParser {
    private String request;

    public RequestParser(String request) {
        this.request = request;
    }

    public String getMethod() {
        return request.split(" ")[0].trim();
    }

    public String getPath() {
        return request.split(" ")[1].trim();
    }

    public String getVersion() {
        return request.split(" ")[2].trim();
    }

    public HashMap getParams(String pathWithParams) {
        String[] splitPathFromParams = pathWithParams.split("\\?");

        HashMap<String, String> params = new HashMap<>();

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

        return params;
    }

    public HashMap getHeaders(String headerInput) {
        HashMap<String, String> headers = new HashMap<>();
        String[] splitUpHeaders = headerInput.split(CRLF);
        for (String headerPair : splitUpHeaders) {
            String[] separatePair = headerPair.split(":");
            String headerName = separatePair[0];
            String headerValue = String.join(":", Arrays.copyOfRange(separatePair, 1, separatePair.length)).trim();
            headers.put(headerName, headerValue);
        }
        return headers;
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

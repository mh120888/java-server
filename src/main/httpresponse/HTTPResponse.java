package httpresponse;

import abstracthttpresponse.AbstractHTTPResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthewhiggins on 7/21/16.
 */
public class HTTPResponse extends AbstractHTTPResponse {
    int status;
    String version = "";
    String body = "";
    HashMap<String, String> headers = new HashMap<>();

    public void setStatus(int statusCode) {
        status = statusCode;
    }

    public void setHTTPVersion(String httpVersion) {
        version = httpVersion;
    }

    public void setBody(String bodyContent) {
        body = bodyContent;
    }

    public void addToBody(String newBodyContent) {
        body += newBodyContent;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public String getFormattedResponse() {
        String result = "";
        result += version + " " + status + "\n";
        result += getFormattedHeaders() + "\n";
        result += body;

        return result;
    }

    String getFormattedHeaders() {
        String result = "";
        for (Map.Entry<String, String> header : headers.entrySet()) {
            result += header.getKey() + ": " + header.getValue() + "\n";
        }
        return result;
    }
}


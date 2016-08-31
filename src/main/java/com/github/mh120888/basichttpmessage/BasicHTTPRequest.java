package com.github.mh120888.basichttpmessage;

import com.github.mh120888.httpmessage.HTTPHeaders;
import com.github.mh120888.httpmessage.HTTPRequest;
import com.github.mh120888.httpmessage.HeaderParser;

import java.util.HashMap;

public class BasicHTTPRequest implements HTTPRequest {

    private RequestParser requestParser;
    private String method;
    private String pathWithParams;
    private String version;
    private HashMap<String, String> headers = new HashMap<>();
    private HashMap<String, String> params = new HashMap<>();
    private String body = "";

    public BasicHTTPRequest() {}

    public void setRequestLine(String requestAsString) {
        requestParser = new RequestParser(requestAsString);

        method = requestParser.getMethod();
        pathWithParams = requestParser.getPath();
        version = requestParser.getVersion();
        params = requestParser.getParams(pathWithParams);
    };

    public void setHeaders(String headerInput) {
        headers = requestParser.getHeaders(headerInput);
    }

    public void addHeader(String headerName, String headerValue) {
        headers.put(headerName, headerValue);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return pathWithParams.split("\\?")[0];
    }

    public String getVersion() {
        return version;
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    public boolean containsHeader(String headerName) {
        return headers.containsKey(headerName);
    }

    public HashMap<String, String> getAllParams() {
        return params;
    }

    public boolean paramExists(String paramName) {
        return params.containsKey(paramName);
    }

    public String getParam(String paramName) {
        return params.get(paramName);
    }

    public String getBaseLocation() {
        return "http://" + headers.getOrDefault(HTTPHeaders.HOST, "localhost:3000") + "/";
    }

    public String getInitialRequestLine() {
        return method + " " + pathWithParams + " " + version;
    }

    public HeaderParser getHeaderParser() {
        return new BasicHeaderParser();
    }

    public void setBody(String input) {
        body = input;
    }

    public String getBody() {
        return body;
    }
}

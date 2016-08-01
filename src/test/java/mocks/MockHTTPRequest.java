package mocks;

import request.Request;
import httprequest.BasicHeaderParser;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/25/16.
 */
public class MockHTTPRequest implements Request {

    private String method;
    private String pathWithParams;
    private String version;
    private HashMap<String, String> headers = new HashMap<>();
    private HashMap<String, String> params = new HashMap<>();
    private String body = "";

    @Override
    public String getPath() {
        return pathWithParams;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    public boolean headerExists(String headerName) {
        return false;
    }

    @Override
    public HashMap<String, String> getAllParams() {
        return params;
    }

    @Override
    public String getParam(String paramName) {
        return params.get(paramName);
    }

    @Override
    public boolean paramExists(String paramName) {
        return params.containsKey(paramName);
    }

    @Override
    public String getBaseLocation() {
        return "http://localhost:5000/";
    }

    @Override
    public String getInitialRequestLine() {
        return method + " " + pathWithParams + " " + version;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public void setRequestLine(String input) {  }

    @Override
    public void setHeaders(String headers) {  }

    public boolean containsHeader(String header) {
        return headers.containsKey(header);
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public BasicHeaderParser getHeaderParser() {
        return new BasicHeaderParser();
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPathWithParams(String pathWithParams) {
        this.pathWithParams = pathWithParams;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void addParam(String paramKey, String paramValue) {
        this.params.put(paramKey, paramValue);
    }

    public void addHeader(String headerKey, String headerValue) {
        this.headers.put(headerKey, headerValue);
    }

}

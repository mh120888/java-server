package mocks;

import abstracthttprequest.AbstractHTTPRequest;
import httprequest.HeaderParser;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/25/16.
 */
public class MockHTTPRequest extends AbstractHTTPRequest {
    @Override
    public String getPath() {
        return null;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public String getHeader(String headerName) {
        return null;
    }

    public boolean headerExists(String headerName) {
        return false;
    }

    @Override
    public HashMap<String, String> getAllParams() {
        return null;
    }

    @Override
    public String getParam(String paramName) {
        return null;
    }

    @Override
    public boolean paramExists(String paramName) {
        return false;
    }

    @Override
    public String getBaseLocation() {
        return null;
    }

    @Override
    public String getInitialRequestLine() {
        return null;
    }

    @Override
    public void setBody(String input) {

    }

    public boolean containsHeader(String header) {
        return true;
    }

    @Override
    public String getBody() {
        return null;
    }

    @Override
    public HeaderParser getHeaderParser() {
        return null;
    }
}
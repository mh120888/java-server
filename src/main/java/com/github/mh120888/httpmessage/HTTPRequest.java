package com.github.mh120888.httpmessage;

import java.util.HashMap;

public interface HTTPRequest {
    String getPath();
    String getMethod();
    String getVersion();
    String getInitialRequestLine();
    String getHeader(String headerName);
    void setHeaders(String headers);
    boolean containsHeader(String headerName);
    HashMap<String, String> getAllParams();
    String getParam(String paramName);
    boolean paramExists(String paramName);
    String getBaseLocation();
    HeaderParser getHeaderParser();
    void setRequestLine(String input);
    void setBody(String input);
    String getBody();
}

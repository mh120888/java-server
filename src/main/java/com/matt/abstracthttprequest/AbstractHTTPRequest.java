package abstracthttprequest;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/20/16.
 */
public abstract class AbstractHTTPRequest {
    public abstract String getPath();
    public abstract String getMethod();
    public abstract String getVersion();
    public abstract String getInitialRequestLine();
    public abstract String getHeader(String headerName);
    public abstract boolean containsHeader(String headerName);
    public abstract HashMap<String, String> getAllParams();
    public abstract String getParam(String paramName);
    public abstract boolean paramExists(String paramName);
    public abstract String getBaseLocation();
    public abstract AbstractHeaderParser getHeaderParser();
    public abstract void setBody(String input);
    public abstract String getBody();
}

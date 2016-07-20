package abstracthttprequest;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/20/16.
 */
public abstract class AbstractHttpRequest {
    public abstract String getPath();
    public abstract String getMethod();
    public abstract String getVersion();
    public abstract HashMap<String, String> getAllHeaders();
    public abstract String getHeader(String headerName);
    public abstract boolean headerExists(String headerName);
    public abstract HashMap<String, String> getAllParams();
    public abstract String getParam(String paramName);
    public abstract boolean paramExists(String paramName);
    public abstract String getBaseLocation();
    public abstract String getInitialRequestLine();
}

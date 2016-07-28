package request;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/20/16.
 */
public interface Request {
    String getPath();
    String getMethod();
    String getVersion();
    String getInitialRequestLine();
    String getHeader(String headerName);
    boolean containsHeader(String headerName);
    HashMap<String, String> getAllParams();
    String getParam(String paramName);
    boolean paramExists(String paramName);
    String getBaseLocation();
    AbstractHeaderParser getHeaderParser();
    void setBody(String input);
    String getBody();
}

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
}

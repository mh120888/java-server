package abstracthttpresponse;

/**
 * Created by matthewhiggins on 7/21/16.
 */
public abstract class AbstractHTTPResponse {
    public abstract void setStatus(int status);
    public abstract void setHTTPVersion(String version);
    public abstract void setBody(String bodyContent);
    public abstract void addToBody(String additionalContent);
    public abstract void addHeader(String name, String value);
}

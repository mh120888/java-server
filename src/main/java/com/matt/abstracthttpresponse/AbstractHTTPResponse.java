package abstracthttpresponse;

/**
 * Created by matthewhiggins on 7/21/16.
 */
public abstract class AbstractHTTPResponse {
    public abstract void setStatus(int status);
    public abstract void setHTTPVersion(String version);
    public abstract void setBodyFromString(String bodyContent);
    public abstract void addHeader(String name, String value);
    public abstract String getFormattedResponse();
    public abstract String getAllButBody();
    public abstract byte[] getBody();
    public abstract void setBody(byte[] body);
}

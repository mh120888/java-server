package response;

/**
 * Created by matthewhiggins on 7/21/16.
 */
public interface Response {
    void setStatus(int status);
    void setHTTPVersion(String version);
    void addHeader(String name, String value);
    String getFormattedResponse();
    String getAllButBody();
    byte[] getBody();
    void setBody(byte[] body);
}

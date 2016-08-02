package httpmessage;

/**
 * Created by matthewhiggins on 7/21/16.
 */
public interface HTTPResponse {
    void setStatus(int status);
    void setHTTPVersion(String version);
    void addHeader(String name, String value);
    String getFormattedResponse();
    String getStatusLineAndHeaders();
    byte[] getBody();
    void setBody(byte[] body);
}

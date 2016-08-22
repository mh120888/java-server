package httpmessage;

public interface HTTPResponse {
    void setStatus(int status);
    void setHTTPVersion(String version);
    void addHeader(String name, String value);
    String getFormattedResponse();
    String getStatusLineAndHeaders();
    byte[] getBody();
    void setBody(byte[] body);
}

package basichttpmessage;

import httpmessage.BasicRequestResponseFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthewhiggins on 8/2/16.
 */
public class BasicHTTPRequestResponseFactory implements BasicRequestResponseFactory {
    public BasicHTTPRequest getNewRequest() {
        return new BasicHTTPRequest();
    }
    public HTTPResponse getNewResponse() {
        return new HTTPResponse();
    }

    /**
     * Created by matthewhiggins on 7/21/16.
     */
    public static class HTTPResponse implements httpmessage.HTTPResponse {
        int status;
        String version = "";
        byte[] body = new byte[0];
        HashMap<String, String> headers = new HashMap<>();

        public void setStatus(int statusCode) {
            status = statusCode;
        }

        public void setHTTPVersion(String httpVersion) {
            version = httpVersion;
        }

        public void setBody (byte[] bodyBytes) {
            body = bodyBytes;
        }

        public byte[] getBody() {
            return body;
        }

        public void addHeader(String name, String value) {
            headers.put(name, value);
        }

        public String getFormattedResponse() {
            String result = getStatusLineAndHeaders();
            result += new String(body);

            return result;
        }

        public String getStatusLineAndHeaders() {
            String result = "";
            result += version + " " + status + " " + getStatusText() + "\n";
            result += getFormattedHeaders() + "\n";

            return result;
        }

        String getFormattedHeaders() {
            String result = "";
            for (Map.Entry<String, String> header : headers.entrySet()) {
                result += header.getKey() + ": " + header.getValue() + "\n";
            }
            return result;
        }

        String getStatusText() {
            switch (status) {
                case 200: return "OK";
                case 204: return "No Content";
                case 206: return "Partial Content";
                case 302: return "Found";
                case 401: return "Not Authorized";
                case 404: return "Not Found";
                case 405: return "Method Not Allowed";
                default:  return "";
            }
        }
    }
}

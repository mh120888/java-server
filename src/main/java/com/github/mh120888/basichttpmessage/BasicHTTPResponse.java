package com.github.mh120888.basichttpmessage;

import com.github.mh120888.httpmessage.HTTPResponse;
import com.github.mh120888.httpmessage.HTTPStatus;

import java.util.HashMap;
import java.util.Map;

import static com.github.mh120888.httpmessage.MessageFormatting.CRLF;

public class BasicHTTPResponse implements HTTPResponse {
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
        result += version + " " + status + " " + getStatusText() + CRLF;
        result += getFormattedHeaders() + CRLF;

        return result;
    }

    String getFormattedHeaders() {
        String result = "";
        for (Map.Entry<String, String> header : headers.entrySet()) {
            result += header.getKey() + ": " + header.getValue() + CRLF;
        }
        return result;
    }

    String getStatusText() {
        return HTTPStatus.getStatusText(status);
    }
}

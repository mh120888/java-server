package httpmessage;

public interface HTTPMessageFactory {
    HTTPRequest getNewRequest();
    HTTPResponse getNewResponse();
}

package httpmessage;

/**
 * Created by matthewhiggins on 8/2/16.
 */
public interface HTTPMessageFactory {
    HTTPRequest getNewRequest();
    HTTPResponse getNewResponse();
}

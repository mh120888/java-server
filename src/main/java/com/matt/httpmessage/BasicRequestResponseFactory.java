package httpmessage;

/**
 * Created by matthewhiggins on 8/2/16.
 */
public interface BasicRequestResponseFactory {
    HTTPRequest getNewRequest();
    HTTPResponse getNewResponse();
}

package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;
import abstracthttpresponse.AbstractHTTPResponse;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public interface ResourceHandler {
//    public HashMap<String, String> getResponseData(AbstractHTTPRequest requestData);
    String getResponseData(AbstractHTTPRequest requestData, AbstractHTTPResponse response);
}

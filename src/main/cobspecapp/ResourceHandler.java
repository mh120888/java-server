package cobspecapp;

import abstracthttprequest.AbstractHttpRequest;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public interface ResourceHandler {
    public HashMap<String, String> getResponseData(AbstractHttpRequest requestData);
}

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public interface ResourceHandler {
    public HashMap<String, String> getResponseData(HashMap<String, String> requestData);
}

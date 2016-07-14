import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public interface ResourceHandler {
    public static final String FILEPATH = "/Users/matthewhiggins/Desktop/cob_spec/public";

    public HashMap<String, String> getResponseData(HashMap<String, String> requestData);
}

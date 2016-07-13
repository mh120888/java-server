import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class ParameterParser {
    public static HashMap<String, String> parse(String path) {
        HashMap<String, String> result = new HashMap<>();
        String[] splitPathFromParams = path.split("\\?");
        if (splitPathFromParams.length > 1) {
            String[] params = splitPathFromParams[1].split("&");
            for (String singleParam: params) {
                String[] paramValuePair = singleParam.split("=");
                result.put(paramValuePair[0], paramValuePair[1]);
            }
        }
        return result;
    }
}

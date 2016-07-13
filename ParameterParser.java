import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class ParameterParser {
    private static Map<String, String> decoderPairs = new HashMap<>();
    static {
        decoderPairs.put("%20", " ");
        decoderPairs.put("%3C", "<");
        decoderPairs.put("%2C", ",");
        decoderPairs.put("%3E", ">");
        decoderPairs.put("%3D", "=");
        decoderPairs.put("%3B", ";");
        decoderPairs.put("%2B", "+");
        decoderPairs.put("%26", "&");
        decoderPairs.put("%40", "@");
        decoderPairs.put("%23", "#");
        decoderPairs.put("%24", "\\$");
        decoderPairs.put("%5B", "[");
        decoderPairs.put("%5D", "]");
        decoderPairs.put("%3A", ":");
        decoderPairs.put("%22", "\"");
        decoderPairs.put("%3F", "\\?");
    }

    public static HashMap<String, String> parse(String path) {
        HashMap<String, String> result = new HashMap<>();
        String[] splitPathFromParams = path.split("\\?");
        if (splitPathFromParams.length > 1) {
            String[] params = splitPathFromParams[1].split("&");
            for (String singleParam: params) {
                String[] paramValuePair = singleParam.split("=");
                String value = paramValuePair[1];
                for (Map.Entry<String, String> entry : decoderPairs.entrySet()) {
                    value = value.replaceAll(entry.getKey(), entry.getValue());
                }
                result.put(paramValuePair[0], value);
            }
        }
        return result;
    }
}

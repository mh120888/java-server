import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class HeaderParser {
    public static HashMap<String, String> parse(String headers) {
        HashMap<String, String> result = new HashMap<>();
        String[] separateHeaders = headers.split("\n");

        for (String headerPair : separateHeaders) {
            String[] separatePair = headerPair.split(":");
            result.put(separatePair[0], separatePair[1].trim());
        }

        return result;
    }
}

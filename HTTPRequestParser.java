import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/10/16.
 */
public class HTTPRequestParser {
    public static HashMap<String, String> parse(String request) {
        String[] splitIRL = request.split(" ");
        HashMap<String, String> result = new HashMap<>();

        result.put("method", splitIRL[0]);
        result.put("path", splitIRL[1]);
        result.put("httpVersion", splitIRL[2]);

        return result;
    }
}

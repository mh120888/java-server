import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by matthewhiggins on 7/10/16.
 */
public class HTTPRequestParser {
    public static HashMap<String, String> parse(String request) {
        String[] breakUpRequest = request.split("\n\n");

        String[] splitIRL = breakUpRequest[0].split(" ");
        HashMap<String, String> result = new HashMap<>();

        result.put("method", splitIRL[0]);
        result.put("path", splitIRL[1]);
        result.put("httpVersion", splitIRL[2]);

        String[] headersArray = breakUpRequest[0].split("\n");
        String[] headers = Arrays.copyOfRange(headersArray, 1, headersArray.length);

        if (headers.length > 0) {
            result.put("headers", String.join("\n", headers));
        }

        if (breakUpRequest.length > 1) {
            result.put("body", breakUpRequest[breakUpRequest.length - 1]);
        }

        return result;
    }
}

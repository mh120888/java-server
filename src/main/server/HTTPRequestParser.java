package server;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/10/16.
 */
public class HTTPRequestParser {
    public static HashMap<String, String> parse(String request) {
        String[] breakUpRequest = request.split("\n");

        String[] splitIRL = breakUpRequest[0].split(" ");
        HashMap<String, String> result = new HashMap<>();

        result.put("method", splitIRL[0]);
        result.put("path", splitIRL[1]);
        result.put("httpVersion", splitIRL[2]);

        if (breakUpRequest.length > 1) {
            String[] headers = Arrays.copyOfRange(breakUpRequest, 1, breakUpRequest.length);
            String headerContent = String.join("\n", headers).trim();
            if (!headerContent.isEmpty()) {
                result.put("headers", String.join("\n", headers));
            }
        }

        return result;
    }
}

//depends on nothing
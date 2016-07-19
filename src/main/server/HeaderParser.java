package server;

import java.util.Arrays;
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
            String headerName = separatePair[0];
            String headerValue = String.join(":", Arrays.copyOfRange(separatePair, 1, separatePair.length)).trim();

            result.put(headerName, headerValue);
        }

        return result;
    }
}

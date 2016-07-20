package server;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/15/16.
 */
public class CommandLineArgsParser {
    public static HashMap<String, String> groupOptions(String[] args) {
        HashMap<String, String> parsedArgs = new HashMap<>();
        if ((args.length % 2) == 0) {
            for (int i = 0; i < args.length - 1; i += 2) {
                String flag = args[i];
                if (flag.charAt(0) == '-') {
                    parsedArgs.put(flag, args[i + 1]);
                }
            }
        }

        return parsedArgs;
    }
}

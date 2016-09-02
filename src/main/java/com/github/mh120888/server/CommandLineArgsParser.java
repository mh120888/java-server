package com.github.mh120888.server;

import java.util.HashMap;

import static com.github.mh120888.httpmessage.MessageFormatting.CRLF;

public class CommandLineArgsParser {
    private final static String usage = "Usage: java -jar javaserver-1.0-SNAPSHOT.jar [-pd]" + CRLF +
            "\t-p <port number> {80}" + CRLF +
            "\t-d <public directory> {.} (required)";

    public static HashMap<String, String> groupOptions(String[] args) {
        HashMap<String, String> parsedArgs = new HashMap<>();
        if (areArgsInPairs(args)) {
            addOptionsToParsedArgs(args, parsedArgs);
        } else {
            printUsageAndExit();
        }

        return parsedArgs;
    }

    static void printUsageAndExit() {
        System.err.println(usage);
        System.exit(64);
    }

    private static void addOptionsToParsedArgs(String[] args, HashMap<String, String> parsedArgs) {
        for (int i = 0; i < args.length - 1; i += 2) {
            String flag = args[i];
            if (flag.charAt(0) == '-') {
                parsedArgs.put(flag, args[i + 1]);
            }
        }
    }

    private static boolean areArgsInPairs(String[] args) {
        return (args.length % 2) == 0;
    }
}


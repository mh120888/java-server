package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;

import java.util.Arrays;

public class Router {

    public static Action route(HTTPRequest request, FileIO fileIO, String publicDirectory) {
        Logger.addLog(request.getInitialRequestLine());
        String path = request.getPath();

        if (isInPublicDirectory(publicDirectory, fileIO, path)) {
            return new StaticResourceAction(publicDirectory);
        } else if (path.contains("/coffee")) {
            return new CoffeeAction();
        } else if (path.contains("/tea")) {
            return new TeaAction();
        } else if (path.contains("/form")) {
            return new PostableAction();
        } else if (path.contains("/logs")) {
            return new LogsAction();
        } else if (path.contains("/parameters")) {
            return new ParametersAction();
        } else if (path.contains("/method_options")) {
            return new OptionsAction();
        } else if (path.contains("/redirect")) {
            return new RedirectAction();
        } else {
            return new NotFoundAction();
        }
    }

    private static boolean isInPublicDirectory(String publicDirectory, FileIO fileIO, String path) {
        String[] fileNames = fileIO.getFilenames(publicDirectory);

        return Arrays.asList(fileNames).contains(path.replace("/", "")) || path.equals("/");
    }
}

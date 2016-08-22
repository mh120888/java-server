package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;

import java.io.File;
import java.util.Arrays;

public class Router {
    public static Action route(HTTPRequest request, String publicDirectory) {
        Logger.addLog(request.getInitialRequestLine());

        File file = new File(publicDirectory);
        String[] fileNames = file.list();
        String path = request.getPath();

        if (Arrays.asList(fileNames).contains(path.replace("/", "")) || path.equals("/")) {
            return new StaticResourceAction(publicDirectory);
        } else if (path.contains("/coffee") || path.contains("/tea")) {
            return new CoffeeAction();
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
}

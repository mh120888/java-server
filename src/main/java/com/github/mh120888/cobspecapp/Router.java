package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Router {
    private final String publicDirectory;
    private FileIO fileIO;
    private final static TreeMap<String, Action> ROUTES = new TreeMap<>();

    public Router(String publicDirectory, FileIO fileIO) {
        this.publicDirectory = publicDirectory;
        this.fileIO = fileIO;
        configureAllRoutes();
    }

    public Action route(HTTPRequest request) {
        Logger.addLog(request.getInitialRequestLine());
        String path = request.getPath();

        return getAction(path);
    }

    private Action getAction(String path) {
        path = path.equals("/") ? "/index" : path;
        Action result = new NotFoundAction();
        for (Map.Entry<String, Action> entry : ROUTES.entrySet()) {
            if (path.contains(entry.getKey())) {
                result = entry.getValue();
            }
        }
        return result;
    }

    private void configureAllRoutes() {
        configureCustomRoutes();
        configureRoutesBasedOnPublicDirectory();
    }

    private void configureCustomRoutes() {
        ROUTES.put("/coffee", new CoffeeAction());
        ROUTES.put("/tea", new TeaAction());
        ROUTES.put("/form", new PostableAction());
        ROUTES.put("/logs", new LogsAction());
        ROUTES.put("/parameters", new ParametersAction());
        ROUTES.put("/method_options", new OptionsAction());
        ROUTES.put("/redirect", new RedirectAction());
    }

    private void configureRoutesBasedOnPublicDirectory() {
        Action staticResourceAction = new StaticResourceAction(publicDirectory);
        String[] fileNames = fileIO.getFilenames(publicDirectory);
        for (String fileName : fileNames){
            ROUTES.put(fileName, staticResourceAction);
        }
        ROUTES.put("/index", staticResourceAction);
    }
}

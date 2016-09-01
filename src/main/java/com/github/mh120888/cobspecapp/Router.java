package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;

import java.util.HashMap;

public class Router {
    private final String publicDirectory;
    private FileIO fileIO;
    private final static HashMap<String, Action> ROUTES = new HashMap<>();

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
        return ROUTES.getOrDefault(path, new NotFoundAction());
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
        ROUTES.put("/method_options2", new OptionsAction());
        ROUTES.put("/redirect", new RedirectAction());
    }

    private void configureRoutesBasedOnPublicDirectory() {
        Action staticResourceAction = new StaticResourceAction(publicDirectory);
        String[] fileNames = fileIO.getFilenames(publicDirectory);
        for (String fileName : fileNames){
            ROUTES.put("/" + fileName, staticResourceAction);
        }
        ROUTES.put("/index", staticResourceAction);
    }
}

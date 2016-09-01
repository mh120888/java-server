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
        String method = request.getMethod();
        String path = request.getPath();

        return getAction(method, path);
    }

    private Action getAction(String method, String path) {
        path = path.equals("/") ? "/index" : path;
        return ROUTES.getOrDefault(method + " " + path, new NotFoundAction());
    }

    private void configureAllRoutes() {
        configureCustomRoutes();
        configureRoutesBasedOnPublicDirectory();
    }

    private void configureCustomRoutes() {
        ROUTES.put("GET /coffee", new CoffeeAction());

        ROUTES.put("GET /tea", new TeaAction());

        ROUTES.put("GET /form", new FormAction());
        ROUTES.put("POST /form", new FormAction());
        ROUTES.put("PUT /form", new FormAction());
        ROUTES.put("DELETE /form", new FormAction());


        ROUTES.put("GET /logs", new LogsAction());

        ROUTES.put("GET /parameters", new ParametersAction());

        ROUTES.put("GET /method_options", new OptionsAction());
        ROUTES.put("PUT /method_options", new OptionsAction());
        ROUTES.put("POST /method_options", new OptionsAction());
        ROUTES.put("HEAD /method_options", new OptionsAction());
        ROUTES.put("OPTIONS /method_options", new OptionsAction());

        ROUTES.put("GET /method_options2", new OptionsAction());
        ROUTES.put("OPTIONS /method_options2", new OptionsAction());

        ROUTES.put("GET /redirect", new RedirectAction());
    }

    private void configureRoutesBasedOnPublicDirectory() {
        Action staticResourceAction = new StaticResourceAction(publicDirectory);
        Action patchStaticResourceAction = new PatchStaticResourceAction(publicDirectory, fileIO);

        String[] fileNames = fileIO.getFilenames(publicDirectory);
        for (String fileName : fileNames){
            ROUTES.put("GET /" + fileName, staticResourceAction);
            ROUTES.put("PATCH /" + fileName, patchStaticResourceAction);
        }
        ROUTES.put("GET /index", staticResourceAction);
        ROUTES.put("HEAD /index", staticResourceAction);
    }
}

package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;

import java.util.LinkedHashMap;

public class Router {
    private final String publicDirectory;
    private FileIO fileIO;
    private final static LinkedHashMap<MethodRoute, Action> ROUTES = new LinkedHashMap<>();
    private MethodNotAllowedAction methodNotAllowedAction = new MethodNotAllowedAction();

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
        return ROUTES.getOrDefault(new MethodRoute(method, path), new NotFoundAction());
    }

    private void configureAllRoutes() {
        configureCustomRoutes();
        configureRoutesBasedOnPublicDirectory();
    }

    private void configureCustomRoutes() {
        addRoute("GET", "/coffee", new CoffeeAction());
        disallowUndeclaredMethodsFor("/coffee");

        addRoute("GET", "/tea", new TeaAction());

        addRoute("GET", "/form", new FormAction());
        addRoute("POST", "/form", new FormAction());
        addRoute("PUT", "/form", new FormAction());
        addRoute("DELETE", "/form", new FormAction());

        addRoute("GET", "/logs", new LogsAction());

        addRoute("GET", "/parameters", new ParametersAction());

        addRoute("GET", "/method_options", new OptionsAction());
        addRoute("PUT", "/method_options", new OptionsAction());
        addRoute("POST", "/method_options", new OptionsAction());
        addRoute("HEAD", "/method_options", new OptionsAction());
        addRoute("OPTIONS", "/method_options", new OptionsAction());

        addRoute("GET", "/method_options2", new OptionsAction());
        addRoute("OPTIONS", "/method_options2", new OptionsAction());

        addRoute("GET", "/redirect", new RedirectAction());
    }

    private void configureRoutesBasedOnPublicDirectory() {
        Action getStaticResourceAction = new GetStaticResourceAction(publicDirectory);
        Action headStaticResourceAction = new HeadStaticResourceAction(publicDirectory);
        Action patchStaticResourceAction = new PatchStaticResourceAction(publicDirectory, fileIO);

        String[] fileNames = fileIO.getFilenames(publicDirectory);
        for (String fileName : fileNames) {
            addRoute("GET", "/" + fileName, getStaticResourceAction);
            addRoute("HEAD", "/" + fileName, headStaticResourceAction);
            addRoute("PATCH", "/" + fileName, patchStaticResourceAction);
            disallowUndeclaredMethodsFor("/" + fileName);
        }
        addRoute("GET", "/index", getStaticResourceAction);
        addRoute("HEAD", "/index", headStaticResourceAction);
    }

    private void addRoute(String method, String path, Action action) {
        ROUTES.put(new MethodRoute(method, path), action);
    }

    private void disallowUndeclaredMethodsFor(String path) {
        ROUTES.put(new MethodRoute("", path), methodNotAllowedAction);
    }
}

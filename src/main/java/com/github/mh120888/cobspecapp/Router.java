package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;

import java.util.LinkedHashMap;

public class Router {
    private final String publicDirectory;
    private FileIO fileIO;
    private final static LinkedHashMap<MethodRoute, Action> ROUTES = new LinkedHashMap<>();

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
        ROUTES.put(new MethodRoute("GET", "/coffee"), new CoffeeAction());
        ROUTES.put(new MethodRoute("", "/coffee"), new MethodNotAllowedAction());

        ROUTES.put(new MethodRoute("GET", "/tea"), new TeaAction());

        ROUTES.put(new MethodRoute("GET", "/form"), new FormAction());
        ROUTES.put(new MethodRoute("POST", "/form"), new FormAction());
        ROUTES.put(new MethodRoute("PUT", "/form"), new FormAction());
        ROUTES.put(new MethodRoute("DELETE", "/form"), new FormAction());

        ROUTES.put(new MethodRoute("GET", "/logs"), new LogsAction());

        ROUTES.put(new MethodRoute("GET", "/parameters"), new ParametersAction());

        ROUTES.put(new MethodRoute("GET", "/method_options"), new OptionsAction());
        ROUTES.put(new MethodRoute("PUT", "/method_options"), new OptionsAction());
        ROUTES.put(new MethodRoute("POST", "/method_options"), new OptionsAction());
        ROUTES.put(new MethodRoute("HEAD", "/method_options"), new OptionsAction());
        ROUTES.put(new MethodRoute("OPTIONS", "/method_options"), new OptionsAction());

        ROUTES.put(new MethodRoute("GET", "/method_options2"), new OptionsAction());
        ROUTES.put(new MethodRoute("OPTIONS", "/method_options2"), new OptionsAction());

        ROUTES.put(new MethodRoute("GET", "/redirect"), new RedirectAction());
    }

    private void configureRoutesBasedOnPublicDirectory() {
        Action getStaticResourceAction = new GetStaticResourceAction(publicDirectory);
        Action headStaticResourceAction = new HeadStaticResourceAction(publicDirectory);
        Action patchStaticResourceAction = new PatchStaticResourceAction(publicDirectory, fileIO);

        String[] fileNames = fileIO.getFilenames(publicDirectory);
        for (String fileName : fileNames){
            ROUTES.put(new MethodRoute("GET", "/" + fileName), getStaticResourceAction);
            ROUTES.put(new MethodRoute("HEAD", "/" + fileName), headStaticResourceAction);
            ROUTES.put(new MethodRoute("PATCH", "/" + fileName), patchStaticResourceAction);
            ROUTES.put(new MethodRoute("",  "/" + fileName), new MethodNotAllowedAction());
        }
        ROUTES.put(new MethodRoute("GET", "/index"), getStaticResourceAction);
        ROUTES.put(new MethodRoute("HEAD", "/index"), headStaticResourceAction);
    }
}

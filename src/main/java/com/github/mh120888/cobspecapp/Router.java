package com.github.mh120888.cobspecapp;

import com.github.mh120888.httpmessage.HTTPRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Router {
    private static String publicDirectory;
    private static FileIO fileIO;
    private final static LinkedHashMap<MethodRoute, Action> ROUTES = new LinkedHashMap<>();
    private final static HashMap<String, List<String>> PERMITTED_METHODS_BY_ROUTE = new HashMap<>();
    private static MethodNotAllowedAction methodNotAllowedAction = new MethodNotAllowedAction();

    public static void initRouter(String directory, FileIO io) {
        publicDirectory = directory;
        fileIO = io;
        configureAllRoutes();
    }

    public static Action route(HTTPRequest request) {
        Logger.addLog(request.getInitialRequestLine());
        String method = request.getMethod();
        String path = request.getPath();

        return getAction(method, path);
    }

    public static List<String> getPermittedMethodsFor(String path) {
        ArrayList<String> emptyList = new ArrayList<>();
        return PERMITTED_METHODS_BY_ROUTE.getOrDefault(path, emptyList);
    }

    private static Action getAction(String method, String path) {
        path = path.equals("/") ? "/index" : path;
        return ROUTES.getOrDefault(new MethodRoute(method, path), new NotFoundAction());
    }

    private static void configureAllRoutes() {
        configureCustomRoutes();
        configureRoutesBasedOnPublicDirectory();
    }

    private static void configureCustomRoutes() {
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

    private static void configureRoutesBasedOnPublicDirectory() {
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

    private static void addRoute(String method, String path, Action action) {
        ROUTES.put(new MethodRoute(method, path), action);
        List<String> list = PERMITTED_METHODS_BY_ROUTE.getOrDefault(path, new ArrayList<String>() {});
        list.add(method);
        PERMITTED_METHODS_BY_ROUTE.put(path, list);
    }

    private static void disallowUndeclaredMethodsFor(String path) {
        ROUTES.put(new MethodRoute("", path), methodNotAllowedAction);
    }
}


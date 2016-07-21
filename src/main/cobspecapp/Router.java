package cobspecapp;

import abstracthttprequest.AbstractHTTPRequest;

import java.io.File;
import java.util.Arrays;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class Router {
    public static ResourceHandler getEndpoint(AbstractHTTPRequest request, String publicDirectory) {
        Logger.addLog(request.getInitialRequestLine());

        File file = new File(publicDirectory);
        String[] fileNames = file.list();
        String path = request.getPath();

        if (Arrays.asList(fileNames).contains(path.replace("/", "")) || path.equals("/")) {
            return new StaticResourceHandler(publicDirectory);
        } else if (path.contains("/coffee") || path.contains("/tea")) {
            return new CoffeeResourceHandler();
        } else if (path.contains("/form")) {
            return new PostableResourceHandler();
        } else if (path.contains("/logs")) {
            return new LogsResourceHandler();
        } else if (path.contains("/parameters")) {
            return new ParametersResourceHandler();
        } else if (path.contains("/method_options")) {
            return new OptionsResourceHandler();
        } else if (path.contains("/redirect")) {
            return new RedirectResourceHandler();
        } else {
            return new NotFoundResourceHandler();
        }
    }
}

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/11/16.
 */
public class Router {
    public static Endpoint getEndpoint(HashMap<String, String> request) {
        File file = new File(Endpoint.FILEPATH);
        String[] fileNames = file.list();
        String path = request.get("path");
        Logger.addLog(request.get("method") + " " + path + " " + request.get("httpVersion"));

        if (Arrays.asList(fileNames).contains(path.replace("/", "")) || path.equals("/")) {
            return new StaticResourceEndpoint();
        } else if (path.contains("/coffee") || path.contains("/tea")) {
            return new CoffeeEndpoint();
        } else if (path.contains("/form")) {
            return new PostableEndpoint();
        } else if (path.contains("/logs")) {
            return new LogsEndpoint();
        } else if (path.contains("/parameters")) {
            return new ParametersEndpoint();
        } else {
            return new NotFoundEndpoint();
        }
    }
}
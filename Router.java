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

        if (Arrays.asList(fileNames).contains(path.replace("/", "")) || path.equals("/")) {
            return new StaticResourceEndpoint();
        } else if (path.equals("/coffee") || path.equals("/tea")) {
            return new CoffeeEndpoint();
        } else {
            return new NotFoundEndpoint();
        }
    }
}
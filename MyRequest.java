import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by matthewhiggins on 7/6/16.
 */

public class MyRequest {
    public String method;
    public String path;
    public String httpversion;

    public MyRequest(String initialRequestLine) {
        String[] splitIRL = initialRequestLine.split(" ");
        method = splitIRL[0];
        path = splitIRL[1];
        httpversion = splitIRL[2];
    }

    public boolean validateMethod() {
        String[] validMethods = {"GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTIONS"};
        boolean result = Arrays.asList(validMethods).contains(method);
        return result;
    }

    public boolean validatePath() {
        File file = new File("/Users/matthewhiggins/Desktop/cob_spec/public");
        String[] fileNames = file.list();
        boolean result = Arrays.asList(fileNames).contains(path.replace("/", ""));
        if (method.equals("POST") || method.equals("PUT") || result || (path.equals("/"))) {
            return true;
        } else {
            return false;
        }
    }
}

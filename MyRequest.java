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
        return Arrays.asList(validMethods).contains(method);
    }

    public boolean validatePath() {
        return (isPathAResource() || isPathAProcess() || method.equals("POST") || method.equals("PUT"));
    }

    public boolean isCoffee() {
        return (path.equals("/coffee"));
    }

    private boolean isPathAResource() {
        File file = new File("/Users/matthewhiggins/Desktop/cob_spec/public");
        String[] fileNames = file.list();
        boolean result = ((path.equals("/")) || Arrays.asList(fileNames).contains(path.replace("/", "")));
        return result;
    }

    private boolean isPathAProcess() {
        String[] pathsThatAreProcesses = {"/tea", "/coffee"};
        return Arrays.asList(pathsThatAreProcesses).contains(path);
    }
}

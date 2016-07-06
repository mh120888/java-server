import java.util.Arrays;

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
        String[] validMethods = {"GET", "POST", "PUT", "PATCH", "DELETE" };
        boolean result = Arrays.asList(validMethods).contains(method);
        return result;
    }
}

/**
 * Created by matthewhiggins on 7/6/16.
 */

public class MyRequest {
    public String method;
    public String path;
    public String httpversion;

    public MyRequest(String initialRequestLine) {
        method = initialRequestLine.split(" ")[0];
        path = initialRequestLine.split(" ")[1];
    }
}

package app;

import java.util.HashMap;

/**
 * Created by matthewhiggins on 7/19/16.
 */
public abstract class Application {
    public abstract HashMap<String, String> getResponse(HashMap<String, String> request);
}

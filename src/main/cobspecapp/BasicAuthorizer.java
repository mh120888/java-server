package cobspecapp;

import java.util.Arrays;
import java.util.Base64;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class BasicAuthorizer {
    public static boolean isAuthorized(String encodedCredentials, String plaintextCredentials) {
        byte[] decodedCredentials = Base64.getDecoder().decode(encodedCredentials);
        byte[] plaintextCredentialsInBytes = plaintextCredentials.getBytes();
        return (Arrays.equals(decodedCredentials, plaintextCredentialsInBytes));
    }
}

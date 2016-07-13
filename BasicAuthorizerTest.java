import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by matthewhiggins on 7/13/16.
 */
public class BasicAuthorizerTest {
    @Test
    public void isAuthorizedReturnsTrueIfEncodedAndPlaintextMatch() {
        String base64EncodedCredentials = "YWRtaW46aHVudGVyMg==";
        String plaintextCredentials = "admin:hunter2";

        assertTrue(BasicAuthorizer.isAuthorized(base64EncodedCredentials, plaintextCredentials));
    }

    @Test
    public void isAuthorizedReturnsFalseIfEncodedAndPlaintextDoNotMatch() {
        String base64EncodedCredentials = "YWRtaW46aHVudGVyMg==";
        String plaintextCredentials = "notcorrect:obviouslywrong";

        assertFalse(BasicAuthorizer.isAuthorized(base64EncodedCredentials, plaintextCredentials));
    }
}
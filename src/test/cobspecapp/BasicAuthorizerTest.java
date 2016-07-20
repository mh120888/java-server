package test;

import cobspecapp.BasicAuthorizer;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
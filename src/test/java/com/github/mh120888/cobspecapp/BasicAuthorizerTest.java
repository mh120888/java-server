package com.github.mh120888.cobspecapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BasicAuthorizerTest {
    String base64EncodedCredentials;

    @Before public void setup() {
        base64EncodedCredentials = "YWRtaW46aHVudGVyMg==";
    }

    @Test
    public void isAuthorizedReturnsTrueIfEncodedAndPlaintextMatch() {
        assertTrue(BasicAuthorizer.isAuthorized(base64EncodedCredentials, "admin:hunter2"));
    }

    @Test
    public void isAuthorizedReturnsFalseIfEncodedAndPlaintextDoNotMatch() {
        assertFalse(BasicAuthorizer.isAuthorized(base64EncodedCredentials, "notcorrect:obviouslywrong"));
    }
}
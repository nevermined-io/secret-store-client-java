package io.keyko.secretstore.helpers;

import org.junit.Test;

import static org.junit.Assert.*;

public class EncryptionHelperTest {

    @Test
    public void encryptSHA256() {
        String input= "my text";
        String encrypted= EncryptionHelper.encryptSHA256(input);

        assertTrue(encrypted.length() >0);
    }
}
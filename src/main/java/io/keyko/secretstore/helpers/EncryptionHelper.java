package io.keyko.secretstore.helpers;

/**
 * Encryption Helper functions
 */
public abstract class EncryptionHelper {

    /**
     * Calculates the SHA-256 digest and returns the value as a hex string
     * @param input string to encrypt
     * @return encrypted string in SHA-256
     */
    public static String encryptSHA256(String input)    {
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(input);
    }
}

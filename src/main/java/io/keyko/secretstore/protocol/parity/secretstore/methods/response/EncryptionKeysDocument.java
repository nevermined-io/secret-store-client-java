package io.keyko.secretstore.protocol.parity.secretstore.methods.response;

import org.json.JSONObject;

public class EncryptionKeysDocument {

    public String common_point;
    public String encrypted_key;
    public String encrypted_point;

    /**
     * Simplified constructor
     */
    public EncryptionKeysDocument() {}

    /**
     * Constructor
     * @param doc json document
     */
    public EncryptionKeysDocument(String doc)   {
        new EncryptionKeysDocument(new JSONObject(doc));
    }

    /**
     * Constructor
     * @param doc JSONObject
     */
    public EncryptionKeysDocument(JSONObject doc)   {
        setCommonPoint(doc.getString("common_point"));
        setEncryptedKey(doc.getString("encrypted_key"));
        setEncryptedPoint(doc.getString("encrypted_point"));
    }

    /**
     * Get common point
     * @return common point
     */
    public String getCommonPoint() {
        return common_point;
    }

    /**
     * Set common point
     * @param commonPoint common point
     */
    public void setCommonPoint(String commonPoint) {
        this.common_point = commonPoint;
    }

    /**
     * Get encrypted key
     * @return encrypted key
     */
    public String getEncryptedKey() {
        return encrypted_key;
    }

    /**
     * Set encrypted key
     * @param encryptedKey encrypted key
     */
    public void setEncryptedKey(String encryptedKey) {
        this.encrypted_key = encryptedKey;
    }

    /**
     * Get encrypted point
     * @return encrypted point
     */
    public String getEncryptedPoint() {
        return encrypted_point;
    }

    /**
     * Set encrypted point
     * @param encryptedPoint encrypted point
     */
    public void setEncryptedPoint(String encryptedPoint) {
        this.encrypted_point = encryptedPoint;
    }

    /**
     * String representation of the object
     * @return
     */
    @Override
    public String toString() {
        return "{" +
                "'commonPoint': '" + common_point + '\'' +
                ",'encryptedKey': '" + encrypted_key + '\'' +
                ",'encryptedPoint': '" + encrypted_point + '\'' +
                '}';
    }
}

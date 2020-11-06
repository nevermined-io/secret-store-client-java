package io.keyko.secretstore.models.secretstore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DecriptionKeys model
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DecriptionKeys {

    @JsonProperty
    public String decrypted_secret;

    @JsonProperty
    public String common_point;

    @JsonProperty
    public List<String> decrypt_shadows;


    /**
     * Reduced constructor
     */
    public DecriptionKeys() {
        this.decrypted_secret= null;
        this.common_point= null;
        this.decrypt_shadows= new ArrayList<>();
    }

    /**
     * Constructor
     * @param decrypted_secret decrypted secret
     * @param common_point common point
     * @param decrypt_shadows decrypt shadows
     */
    public DecriptionKeys(String decrypted_secret, String common_point, ArrayList<String> decrypt_shadows) {
        this.decrypted_secret = decrypted_secret;
        this.common_point = common_point;
        this.decrypt_shadows = decrypt_shadows;
    }

    /**
     * Object initialized
     * @param mapper ObjectMapper
     * @param json json content
     * @return DecryptionKeys
     * @throws IOException Error parsing json
     */
    public static DecriptionKeys builder(ObjectMapper mapper, String json) throws IOException {
        return mapper.readValue(json, DecriptionKeys.class);

    }

    /**
     * Get decrypted secret
     * @return decrypted secret
     */
    public String getDecryptedSecret() {
        return decrypted_secret;
    }

    /**
     * Set decrypted secret
     * @param decrypted_secret
     * @return DecryptionKeys
     */
    public DecriptionKeys setDecryptedSecret(String decrypted_secret) {
        this.decrypted_secret = decrypted_secret;
        return this;
    }

    /**
     * Get common point
     */
    public String getCommonPoint() {
        return common_point;
    }

    /**
     * Set common point
     * @param common_point common point
     * @return DecryptionKeys
     */
    public DecriptionKeys setCommonPoint(String common_point) {
        this.common_point = common_point;
        return this;
    }

    /**
     * Get decrypt shadows
     * @return list of decrypted shadows
     */
    public List<String> getDecryptShadows() {
        return decrypt_shadows;
    }

    /**
     * Set decrypt shadows
     * @param decrypt_shadows
     * @return DecryptionKeys
     */
    public DecriptionKeys setDecryptShadows(List<String> decrypt_shadows) {
        this.decrypt_shadows = decrypt_shadows;
        return this;
    }

    /**
     * String representation of the object
     * @return
     */
    @Override
    public String toString() {
        return "DecriptionKeys{" +
                "decrypted_secret='" + decrypted_secret + '\'' +
                ", common_point='" + common_point + '\'' +
                ", decrypt_shadows=" + decrypt_shadows +
                '}';
    }
}

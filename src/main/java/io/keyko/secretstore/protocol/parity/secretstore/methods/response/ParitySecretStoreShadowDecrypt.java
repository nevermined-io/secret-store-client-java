package io.keyko.secretstore.protocol.parity.secretstore.methods.response;

import org.web3j.protocol.core.Response;

public class ParitySecretStoreShadowDecrypt extends Response<String> {

    /**
     * Get decrypted document
     * @return decrypted document
     */
    public String getDecryptedDocument() {
        return getResult();
    }
}

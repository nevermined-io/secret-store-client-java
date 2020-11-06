package io.keyko.secretstore.protocol.parity.secretstore.methods.response;

import org.web3j.protocol.core.Response;

public class ParitySecretStoreSignRawHash extends Response<String> {
    /**
     * Get signed document key id
     * @return document key id
     */
    public String getSignedDocumentKeyId() {
        return getResult();
    }
}

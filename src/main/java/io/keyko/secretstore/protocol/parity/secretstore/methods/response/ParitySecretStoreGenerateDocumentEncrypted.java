package io.keyko.secretstore.protocol.parity.secretstore.methods.response;

import org.web3j.protocol.core.Response;

public class ParitySecretStoreGenerateDocumentEncrypted  extends Response<String> {
    /**
     * Get the encrypted document
     * @return string
     */
    public String getEncryptedDocument() {
        return getResult();
    }
}

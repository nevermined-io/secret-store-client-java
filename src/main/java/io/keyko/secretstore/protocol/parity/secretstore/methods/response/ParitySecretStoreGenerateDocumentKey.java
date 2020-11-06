package io.keyko.secretstore.protocol.parity.secretstore.methods.response;

import org.web3j.protocol.core.Response;

public class ParitySecretStoreGenerateDocumentKey extends Response<EncryptionKeysDocument> {

    /**
     * Get the encryption keys document object
     * @return EncryptionKeysDocument
     */
    public EncryptionKeysDocument getEncryptionKeysDocument() {
        return getResult();
    }

    /**
     * Return the EncryptionKeysDocument in string format
     * @return string
     */
    public String getStringResult() {
        return getResult().toString();
    }


}

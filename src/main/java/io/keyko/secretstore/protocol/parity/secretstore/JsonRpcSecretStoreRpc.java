package io.keyko.secretstore.protocol.parity.secretstore;

import io.keyko.secretstore.protocol.parity.secretstore.methods.response.ParitySecretStoreGenerateDocumentEncrypted;
import io.keyko.secretstore.protocol.parity.secretstore.methods.response.ParitySecretStoreShadowDecrypt;
import io.keyko.secretstore.protocol.parity.secretstore.methods.response.ParitySecretStoreGenerateDocumentKey;
import io.keyko.secretstore.protocol.parity.secretstore.methods.response.ParitySecretStoreSignRawHash;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.JsonRpc2_0Admin;
import org.web3j.protocol.core.Request;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * JSON-RPC 2.0 factory implementation for Parity. It wraps the secretstore api requests to the Parity EVM
 */
public class JsonRpcSecretStoreRpc extends JsonRpc2_0Admin {

    /**
     * Constructor
     * @param web3jService Web3jService instance
     */
    public JsonRpcSecretStoreRpc(Web3jService web3jService) {
        super(web3jService);
    }

    /**
     * Constructor
     * @param web3jService Web3jService instance
     * @param pollingInterval polling interval to query the evm
     * @param scheduledExecutorService ScheduledExecutorService
     */
    public JsonRpcSecretStoreRpc(Web3jService web3jService, long pollingInterval, ScheduledExecutorService scheduledExecutorService) {
        super(web3jService, pollingInterval, scheduledExecutorService);
    }

    /**
     * Invoke the secretstore_signRawHash method
     * @param accountId ethereum account id
     * @param password password
     * @param docKeyId document key
     * @return signed hash
     */
    public Request<?, ParitySecretStoreSignRawHash> paritySecretStoreSignRawHash(
            String accountId, String password, String docKeyId) {

        return new Request<>(
                "secretstore_signRawHash",
                Arrays.asList(accountId, password, docKeyId),
                web3jService,
                ParitySecretStoreSignRawHash.class);
    }

    /**
     * Invoke the secretstore_generateDocumentKey method and generate the document key
     * @param accountId ethereum account id
     * @param password password
     * @param serverKey server key
     * @return document key
     */
    public Request<?, ParitySecretStoreGenerateDocumentKey> paritySecretStoreGenerateDocumentKey(
            String accountId, String password, String serverKey) {

        return new Request<>(
                "secretstore_generateDocumentKey",
                Arrays.asList(accountId, password, serverKey),
                web3jService,
                ParitySecretStoreGenerateDocumentKey.class);
    }


    /**
     * Invoke the secretstore_encrypt method to encrypt a document
     * @param accountId ethereum account id
     * @param password password
     * @param encryptedKey encrypted key
     * @param document document to encrypt
     * @return encrypted document
     */
    public Request<?, ParitySecretStoreGenerateDocumentEncrypted> paritySecretStoreDocumentEncrypt(
            String accountId, String password, String encryptedKey, String document) {

        return new Request<>(
                "secretstore_encrypt",
                Arrays.asList(accountId, password, encryptedKey, document),
                web3jService,
                ParitySecretStoreGenerateDocumentEncrypted.class);
    }

    /**
     * Invoke the secretstore_shadowDecrypt to decrypt the document
     * @param accountId ethereum account id
     * @param password account password
     * @param decryptedSecret decrypted secret
     * @param commonPoint common point
     * @param decryptShadows decrypted shadows
     * @param encryptedDocument encrypted documents
     * @return decrypted document
     */
    public Request<?, ParitySecretStoreShadowDecrypt> paritySecretStoreDocumentDecrypt(
            String accountId, String password, String decryptedSecret, String commonPoint, List<String> decryptShadows, String encryptedDocument) {

        return new Request<>(
                "secretstore_shadowDecrypt",
                Arrays.asList(accountId, password, decryptedSecret, commonPoint, decryptShadows, encryptedDocument),
                web3jService,
                ParitySecretStoreShadowDecrypt.class);
    }

}


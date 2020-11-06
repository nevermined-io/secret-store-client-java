package io.keyko.secretstore.auth;

import io.keyko.secretstore.core.SecretStoreDto;
import io.keyko.secretstore.models.secretstore.DecriptionKeys;
import io.keyko.secretstore.core.EvmDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Class useful to abstract all the EVM and Secret Store requests allowing to a user to decrypt a document
 */
public class ConsumerWorker {

    /**
     * Logger
     */
    protected static final Logger log = LogManager.getLogger(ConsumerWorker.class);

    /**
     * Secret Store interface instance
     */
    private SecretStoreDto ssDto;

    /**
     * Parity EVM interface instance
     */
    private EvmDto evmDto;

    /**
     * Consumer constructor. Initialize the Secret Store and Parity EVM connections using the urls and connection parameters
     * @param ssUrl Secret Store URL (i.e: http://localhost:8010/)
     * @param evmUrl Parity EVM URL (i.e: http://localhost:8545/)
     * @param address Consumer Ethereum address (i.e: 0xb3e6499f2b07817ee8e35c8e63cb200df2055d91)
     * @param password Consumer Ethereum account password
     */
    public ConsumerWorker(String ssUrl, String evmUrl, String address, String password)  {
        this(
                SecretStoreDto.builder(ssUrl),
                EvmDto.builder(evmUrl, address, password)
        );
    }

    /**
     * Consumer constructor. It assigns the Secret Store and Parity EVM DTO's
     * @param ssDto Secret Store DTO object (SecretStoreDto.class)
     * @param evmDto Parity EVM DTO object (EvmDto.class)
     */
    public ConsumerWorker(SecretStoreDto ssDto, EvmDto evmDto)  {
        this.ssDto= ssDto;
        this.evmDto= evmDto;
    }

    public String getSignedDocumentKeyId(String documentKeyId) throws IOException {
        return evmDto.signDocumentKeyId(documentKeyId);
    }

    /**
     * Given a documentId and an encrypted document, the method negotiate with the Parity EVM and Secret Store to
     * decrypt the document.
     * If in the Secret Store the acl_contract attribute is specified, an on-chain access control validation
     * will be performed.
     * @param documentKeyId Identifier of the document
     * @param encryptedDocument Encrypted document
     * @return Document content decrypted
     * @throws IOException The document was not decrypted
     */
    public String decryptDocument(String documentKeyId, String encryptedDocument) throws IOException {

        String document;
        String signedDocKey;

        try {
            log.debug("Consuming Document using address:" + evmDto.getAddress());

            log.debug("EVM: Signing Document Key");
            signedDocKey = evmDto.signDocumentKeyId(documentKeyId);

            log.debug("SecretStore: Retrieving document keys");
            DecriptionKeys decriptionKeys= ssDto.retrieveDocumentKeys(documentKeyId, signedDocKey);

            log.debug("EVM: Shadow Decrypt of Document");
            document= evmDto.shadowDecrypt(
                    decriptionKeys.getDecryptedSecret(),
                    decriptionKeys.getCommonPoint(),
                    decriptionKeys.getDecryptShadows(),
                    encryptedDocument);

            if (null == document || document.length() == 0)
                throw new IOException("Unable to decrypt document");

        } catch (Exception e) {
            log.error("Exception: Unable to consume document: " + e.getMessage());
            throw new IOException("Unable to decrypt document");
        }

        return document;
    }

}

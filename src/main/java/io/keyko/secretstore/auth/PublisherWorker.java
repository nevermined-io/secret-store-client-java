package io.keyko.secretstore.auth;

import io.keyko.secretstore.core.EvmDto;
import io.keyko.secretstore.core.SecretStoreDto;
import io.keyko.secretstore.helpers.SecretStoreHelper;
import io.keyko.secretstore.models.HttpResponse;
import io.keyko.secretstore.protocol.parity.secretstore.methods.response.EncryptionKeysDocument;
import org.apache.commons.httpclient.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;

/**
 * Class useful to abstract all the EVM and Secret Store requests allowing to a user to encrypt a document
 */
public class PublisherWorker {

    /**
     * Logger
     */
    protected static final Logger log = LogManager.getLogger(PublisherWorker.class);

    private final int DEFAULT_THRESHOLD= 0;

    /**
     * Secret Store interface instance
     */
    private SecretStoreDto ssDto;

    /**
     * Parity EVM interface instance
     */
    private EvmDto evmDto;

    /**
     * Publisher constructor. Initialize the Secret Store and Parity EVM connections using the urls and connection parameters
     * @param ssUrl Secret Store URL (i.e: http://localhost:8010/)
     * @param evmUrl Parity EVM URL (i.e: http://localhost:8545/)
     * @param address Publisher Ethereum address (i.e: 0xb3e6499f2b07817ee8e35c8e63cb200df2055d91)
     * @param password Publisher Ethereum account password
     */
    public PublisherWorker(String ssUrl, String evmUrl, String address, String password)  {
        this(
                SecretStoreDto.builder(ssUrl),
                EvmDto.builder(evmUrl, address, password)
        );
    }

    /**
     * Publisher constructor. It assigns the Secret Store and Parity EVM DTO's
     * @param ssDto Secret Store DTO object (SecretStoreDto.class)
     * @param evmDto Parity EVM DTO object (EvmDto.class)
     */
    public PublisherWorker(SecretStoreDto ssDto, EvmDto evmDto) {
        this.ssDto= ssDto;
        this.evmDto= evmDto;
    }

    public String encryptDocument(String documentKeyId, String document) throws IOException {
        return encryptDocument(documentKeyId, document, DEFAULT_THRESHOLD);
    }

    /**
     * Given a documentKeyId and a document, the method negotiate with the Parity EVM and Secret Store to
     * encrypt the document and store the decryption keys in the Secret Store.
     * If in the Secret Store the acl_contract attribute is specified, an on-chain access control validation
     * will be performed in the consumption.
     * @param documentKeyId Identifier of the document
     * @param document Document content
     * @param threshold minimum number of secret store nodes necessary
     * @return Document content encrypted
     * @throws IOException The document was not published correctly
     */
    public String encryptDocument(String documentKeyId, String document, int threshold) throws IOException {

        String signedDocKey;
        String docEncrypted;

        try {
            log.debug("Encrypt Document using address:" + evmDto.getAddress());

            log.debug("EVM: Signing documentKeyId");
            signedDocKey = evmDto.signDocumentKeyId(documentKeyId);

            log.debug("SecretStore: Generating Secret Store Server key");
            String ssServerKey= SecretStoreHelper.removeQuotes(
                    ssDto.generateServerKey(documentKeyId, signedDocKey, String.valueOf(threshold)));

            log.debug("EVM: Generate the Document key from the Secret Store key");
            EncryptionKeysDocument docKeys = evmDto.generateDocumentKeyFromKey(ssServerKey);

            log.debug("EVM: Document encryption");
            docEncrypted= evmDto.documentEncryption(docKeys.getEncryptedKey(), document);

            log.debug("SecretStore: Store the Document key");
            HttpResponse result= ssDto.storeDocumentKey(documentKeyId, signedDocKey, docKeys.getCommonPoint(), docKeys.getEncryptedPoint());

            log.debug("Document Stored " + result.getBody());

        } catch (HttpException e) {
            log.error("HttpException: Unable to register the document: " + e.getMessage());
            throw new IOException("Unable to encrypt document");
        } catch (IOException e) {
            log.error("IOException: Unable to register the document: " + e.getMessage());
            throw new IOException("Unable to encrypt document");
        } catch (Exception e) {
            log.error("Exception: Unable to register the document: " + e.getMessage());
            throw new IOException("Unable to encrypt document");
        }

        return docEncrypted;
    }


}

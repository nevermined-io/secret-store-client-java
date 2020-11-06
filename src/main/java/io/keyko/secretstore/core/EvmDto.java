package io.keyko.secretstore.core;

import io.keyko.secretstore.helpers.EncodingHelper;
import io.keyko.secretstore.protocol.parity.secretstore.JsonRpcSecretStoreRpc;
import io.keyko.secretstore.protocol.parity.secretstore.methods.response.EncryptionKeysDocument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.List;

/**
 * Parity EVM Client
 */
public class EvmDto {

    protected static final Logger log = LogManager.getLogger(EvmDto.class);

    private String evmUrl;
    private Web3j web3;
    private JsonRpcSecretStoreRpc ssRpc;
    private String address;
    private String password;

    /**
     * Initializes the EvmDto object given a EVM url, user and password
     * @param url Parity EVm url (ie. http://localhost:8545)
     * @param address User ethereum address
     * @param password User password
     * @return EvmDto
     */
    public static EvmDto builder(String url, String address, String password) {
        return new EvmDto(url, address, password);
    }

    private EvmDto(String url, String address, String password)    {
        log.debug("Initializing Evm Dto: " + url);
        this.address= address;
        this.password= password;
        this.evmUrl= url;
        this.web3 = Web3j.build(new HttpService(this.evmUrl));
        this.ssRpc= new JsonRpcSecretStoreRpc(new HttpService(this.evmUrl));

    }


    /**
     * Sign a document key id
     * @param documentKeyId document key id
     * @return A signed document key id
     * @throws IOException Error signing the key through the evm client
     */
    public String signDocumentKeyId(String documentKeyId) throws IOException {
        if (!documentKeyId.startsWith("0x"))
            documentKeyId= "0x" + documentKeyId;
        log.debug("Signing DocumentKeyId: " + documentKeyId);
        return ssRpc.paritySecretStoreSignRawHash(address, password, documentKeyId)
                .send()
                .getResult();

    }

    /**
     * Generates Document key from server key
     * @param serverKey server key
     * @return EncryptionKeysDocument
     * @throws IOException Error generating the document key through the evm client
     */
    public EncryptionKeysDocument generateDocumentKeyFromKey(String serverKey) throws IOException {
        log.debug("Generating DocumentKey for address: " + address);
        return ssRpc.paritySecretStoreGenerateDocumentKey(address, password, serverKey)
                .send()
                .getEncryptionKeysDocument();

    }

    /**
     * Encrypt a document given a key
     * @param encryptedKey encrypted key
     * @param document document to encrypt
     * @return content of the document encrypted
     * @throws IOException Error encrypting the document through the evm client
     */
    public String documentEncryption(String encryptedKey, String document) throws IOException {
        log.debug("Encrypting document from address: " + address);
        return ssRpc.paritySecretStoreDocumentEncrypt(
                    address,
                    password,
                    encryptedKey,
                    "0x" + EncodingHelper.encodeToHex(document))
                .send()
                .getResult();
    }

    /**
     * Decrypt a document given the keys
     * @param decryptedSecret decrypted secret
     * @param commonPoint common point
     * @param decryptShadows decrypt shadows
     * @param encryptedDocument encrypted document
     * @return decrypted document
     * @throws IOException  Error decrypting the document through the evm client
     */
    public String shadowDecrypt(String decryptedSecret, String commonPoint, List<String> decryptShadows, String encryptedDocument) throws IOException {
        log.debug("Decryption document requested from address: " + address);
        return EncodingHelper.decodeHex(
                ssRpc.paritySecretStoreDocumentDecrypt(
                    address,
                    password,
                        decryptedSecret,
                    commonPoint,
                    decryptShadows,
                    encryptedDocument)
                .send()
                .getDecryptedDocument()
        );
    }

    public String getAddress() {
        return address;
    }


}

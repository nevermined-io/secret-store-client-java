package io.keyko.secretstore.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.keyko.secretstore.helpers.EncodingHelper;
import io.keyko.secretstore.models.HttpResponse;
import io.keyko.secretstore.models.secretstore.DecriptionKeys;
import io.keyko.secretstore.helpers.HttpHelper;
import io.keyko.secretstore.protocol.parity.secretstore.JsonRpcSecretStoreRpc;
import org.apache.commons.httpclient.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Secret Store Data Transfer Object (DTO)
 */
public class SecretStoreDto {

    protected static final Logger log = LogManager.getLogger(SecretStoreDto.class);

    private static SecretStoreDto dto= null;
    private JsonRpcSecretStoreRpc ssRpc= null;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String ssUrl;
    private final String THRESHOLD = "0";

    /**
     * Initialize the SecretStoreDto given the Secret Store url
     * @param url secret store url
     * @return SecretStoreDto
     */
    public static SecretStoreDto builder(String url) {
        return new SecretStoreDto(url);
    }


    private SecretStoreDto(String url)    {
        log.debug("Initializing Secret Store Dto: " + url);
        this.ssUrl= url;
    }


    /**
     * Given a document key and a signed document key generates and return a server key
     * @param documentKey document key
     * @param signedDocKey signed document key
     * @return server key
     * @throws HttpException Invalid Http request
     * @throws IOException Unable to generate Server Key
     */
    public String generateServerKey(String documentKey, String signedDocKey) throws HttpException, IOException {
        return generateServerKey(documentKey, signedDocKey, THRESHOLD);
    }

    /**
     * Given a document key and a signed document key generates and return a server key.
     * It requires a threshold
     * @param documentKey document key
     * @param signedDocKey signed document key
     * @param threshold threshold of number of secret store instances
     * @return server key
     * @throws IOException
     */
    public String generateServerKey(String documentKey, String signedDocKey, String threshold) throws IOException {
        String url= ssUrl + "/shadow/" +
                documentKey + "/" +
                EncodingHelper.removeEthereumAddressPrefix(signedDocKey) + "/" +
                threshold;

        String result= HttpHelper.httpClientPostBody(url);
        if (null == result || result.isEmpty())
            throw new IOException("Unable to generate Server Key");
        return result;
    }

    /**
     * Stores a document key in the Secret Store network
     * @param documentKey document key
     * @param signedDocKey signed document key
     * @param commonPoint common point
     * @param encryptedPoint encrypted point
     * @return A HttpResponse of the request
     * @throws HttpException Http request problem
     * @throws IOException Unable to store Document query
     */
    public HttpResponse storeDocumentKey(String documentKey, String signedDocKey, String commonPoint, String encryptedPoint) throws HttpException, IOException {
        String url= ssUrl + "/shadow/" +
                documentKey + "/" +
                EncodingHelper.removeEthereumAddressPrefix(signedDocKey) + "/" +
                EncodingHelper.removeEthereumAddressPrefix(commonPoint) + "/" +
                EncodingHelper.removeEthereumAddressPrefix(encryptedPoint);

        log.debug("Storing Document key: " + url);

        HttpResponse result= HttpHelper.httpClientPost(url);
        if (null == result || result.getStatusCode() != 200)
            throw new IOException("Unable to store Document query");
        return result;
    }


    /**
     * Retrieve document keys from Secret Store
     * @param documentKey document key
     * @param signedDocKey signed document key
     * @return DecryptionKeys object
     * @throws IOException Unable to retrieve document keys
     */
    public DecriptionKeys retrieveDocumentKeys(String documentKey, String signedDocKey) throws IOException {
        String url= ssUrl + "/shadow/" +
                documentKey + "/" +
                EncodingHelper.removeEthereumAddressPrefix(signedDocKey);

        log.debug("EVM: retrieveDocumentKeys(url): " + url);
        HttpResponse response= HttpHelper.httpClientGet(url);
        if (null == response || response.getStatusCode() != 200)
            throw new IOException("Unable to retrieve document keys");
        return DecriptionKeys.builder(objectMapper, response.getBody());
    }



}

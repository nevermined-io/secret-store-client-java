package io.keyko.secretstore.protocol.parity.secretstore;

import io.keyko.secretstore.helpers.SecretStoreHelper;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

import static org.junit.Assert.*;

public class JsonRpcSecretStoreRpcIT {

    private JsonRpcSecretStoreRpc rpc;
    private JSONObject jsonWallet;
    private static final String evmUrl= "http://localhost:8545";
    private final String address= "0xb3e6499f2b07817ee8e35c8e63cb200df2055d91";
    private final String password= "1234";
    private final String did= "did:ocn:0x1234";
    private String encryptedDid;
    private final String SIGNED_HASH = "0x88122b123958bb1eda7763b61bec046d3819365ed5ccfa9c535df2dffacfebf65cd5a33371ad37f2f9651b5475610c74bc06dd237e3559e3f4a41cf0a46f0fe000";
//    private static final String KEYSTORE_PATH= System.getProperty("user.home") + "/.ethereum/keystore/db.users/keys/DevelopmentChain/";
//    private static final String WALLET_PASS= "1234";
//    private static String walletFile;

    @Before
    public void setUp() throws Exception {
        rpc= new JsonRpcSecretStoreRpc(new HttpService(evmUrl));

//        walletFile= Web3Helper.createFullWalletFile(KEYSTORE_PATH, WALLET_PASS);
//        address= "0x" + Web3Helper.getAddressFromWalletFilename(walletFile);
        encryptedDid= "0x" + SecretStoreHelper.generateDocumentKeyId(did);
    }
;
    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void paritySecretStoreSignRawHash() throws IOException {

        String result= rpc.paritySecretStoreSignRawHash(address, password, encryptedDid)
                .send()
                .getResult();

        assertTrue(result.length()>32);
        assertEquals(SIGNED_HASH, result);
    }


}
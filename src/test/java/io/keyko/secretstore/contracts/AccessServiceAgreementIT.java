package io.keyko.secretstore.contracts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;

public class AccessServiceAgreementIT {

    protected static final Logger log = LogManager.getLogger(AccessServiceAgreementIT.class);

    private static Web3j web3= null;
    private static TransactionManager txManager;
    private static Credentials credentials;
    private static final String TEST_ADDRESS= "0x00a329c0648769A73afAc7F9381E08FB43dBEA72";
    private static final String TEST_PASSWORD= "";
    private static final String TEST_FILE= "src/test/resources/8fff55a4-dbac-b059-6b8b-bfc7394ed98a.json.testaccount";

    private static final String CONTRACT_ADDRESS= "0x783f7d86c2634474d84636b56f5cc7dfd360ab15";

    private static final String ALICE_ADDRESS= "0x9629f11b7a43f44892d9722b4bfc0675c7cc4bf9";
    private static final String BOB_ADDRESS= "0x9629f11b7a43f44892d9722b4bfc0675c7cc4bf9";
    private static final String CHARLIE_ADDRESS= "0x5142f0aedc68ed0997257479b748df9b1e39f7e3";
    private static final String VM_URL= "http://localhost:8545";
    private static final BigInteger GAS_PRICE= BigInteger.valueOf(1500);
    private static final BigInteger GAS_LIMIT= BigInteger.valueOf(250000);

/*
    @BeforeClass
    public static void setUp() throws Exception {

        credentials = WalletUtils.loadCredentials(TEST_PASSWORD, TEST_FILE);

        web3 = Web3j.build(new HttpService(VM_URL));
        txManager= new RawTransactionManager(web3, credentials);
        serviceAgreement= io.keyko.keeper.contracts.AccessConditions.load(CONTRACT_ADDRESS, web3, txManager ,GAS_PRICE, GAS_LIMIT);
    }

    @AfterClass
    public static void tearDown() throws Exception {
    }

    @Test
    public void checkPermissions() throws Exception {
        String docId= "123";
        byte[] docIdBytes= EncodingHelper.stringToBytes32(docId).getValue();
        log.debug("Encoding " + docId + " = " + docIdBytes);

        TransactionReceipt receipt= serviceAgreement.grantPermissions(BOB_ADDRESS, docIdBytes).send();

        assertTrue(receipt.getBlockNumber().intValue() >0);

        Boolean bobStatus= serviceAgreement.checkPermissions(BOB_ADDRESS, docIdBytes).send();
        assertTrue( bobStatus );

        Boolean charlieStatus= false;
        try {
            receipt= serviceAgreement.revokePermissions(CHARLIE_ADDRESS, docIdBytes).send();
            charlieStatus = serviceAgreement.checkPermissions(CHARLIE_ADDRESS, docIdBytes).send();
        } catch (ContractCallException ex)  {
            log.debug("Charlie doesn't fulfill the requirements");
        }

        assertFalse( charlieStatus );

        Boolean otherStatus= false;
        try {
            otherStatus = serviceAgreement.checkPermissions(TEST_ADDRESS, docIdBytes).send();
        } catch (ContractCallException ex)  {
            log.debug("Test user doesn't fulfill the requirements");
        }
        assertFalse( otherStatus );

    }

*/

}
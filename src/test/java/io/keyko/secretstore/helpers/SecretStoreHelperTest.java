package io.keyko.secretstore.helpers;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class SecretStoreHelperTest {

    @Test
    public void generateDocumentKeyId() {
        String docId= SecretStoreHelper.generateDocumentKeyId("123");
        assertTrue(docId.length()>0);
    }

    @Test
    public void generateDocumentKeyIdTwice() {
        String uuid= UUID.randomUUID().toString();
        String docId= SecretStoreHelper.generateDocumentKeyId(uuid);
        String docId2= SecretStoreHelper.generateDocumentKeyId(uuid);

        assertEquals(docId, docId2);
    }

    @Test
    public void removeQuotes() {

        assertEquals("123", SecretStoreHelper.removeQuotes("\"123\""));

        assertEquals("123", SecretStoreHelper.removeQuotes("\"\"\"123\""));
    }
}
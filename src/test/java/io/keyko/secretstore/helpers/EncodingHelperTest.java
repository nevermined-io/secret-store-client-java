package io.keyko.secretstore.helpers;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class EncodingHelperTest {

    @Test
    public void encodeAndDecodeHex() throws UnsupportedEncodingException {
        String input= "abc";
        String encoded= EncodingHelper.encodeToHex(input);
        String output= EncodingHelper.decodeHex(encoded);

        assertEquals(input, output);
    }

    @Test
    public void removeEthereumAddressPrefix() {

        assertEquals("123", EncodingHelper.removeEthereumAddressPrefix("0x123"));
        assertEquals("0x", EncodingHelper.removeEthereumAddressPrefix("0x0x"));
    }
}
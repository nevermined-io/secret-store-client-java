package io.keyko.secretstore.helpers;

import org.apache.commons.codec.binary.StringUtils;
import org.web3j.abi.datatypes.generated.Bytes32;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;

/**
 * Enconding Helper functions
 */
public abstract class EncodingHelper {

    /**
     * Encodes a String in Hex
     * @param input string to encode
     * @return Hex string
     * @throws UnsupportedEncodingException Error encoding to Hex
     */
    public static String encodeToHex(String input) throws UnsupportedEncodingException {
        return DatatypeConverter.printHexBinary(input.getBytes("UTF-8"));
    }

    /**
     * Decodes a Hex String
     * @param input string to decode
     * @return Decoded string
     * @throws UnsupportedEncodingException Error decoding from Hex
     */
    public static String decodeHex(String input) throws UnsupportedEncodingException {
        return new String(
                DatatypeConverter.parseHexBinary(
                        removeEthereumAddressPrefix(input)), "UTF-8"
        );
    }

    /**
     * If exists, removes the "0x" prefix of a String
     * @param address string including ethereum address
     * @return String without the 0x prefix
     */
    public static String removeEthereumAddressPrefix(String address)    {
        if (address.startsWith("0x"))
            return address.replaceFirst("0x", "");
        return address;
    }

    /**
     * Given a String return a Bytes32
     * @param input input string
     * @return Bytes32 output
     */
    public static Bytes32 stringToBytes32(String input) {
        byte[] byteValue = input.getBytes();
        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
        return new Bytes32(byteValueLen32);
    }

    /**
     * Given a String return a byte[]
     * @param input input string
     * @return byte[] output
     */
    public static byte[] stringToByteArray(String input) {
        int len = input.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(input.charAt(i), 16) << 4)
                    + Character.digit(input.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * Given a byte[] convert to string
     * @param input byte[]
     * @return String
     */
    public static String bytes32ToString(byte[] input) {
        return StringUtils.newStringUtf8(input);
    }



}

package util;

import java.security.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encryption class used in Bittrex ApiInterface
 */
public class Encryption {

    private static final String HmacSHA512 = "HmacSHA512";

    public static String calculateHash(String secret, String url) {
        Mac shaHmac = null;
        try {
            shaHmac = Mac.getInstance(HmacSHA512);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), HmacSHA512);
        try {
            assert shaHmac != null;
            shaHmac.init(secretKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] hash = shaHmac.doFinal(url.getBytes());
        StringBuilder stringHash = new StringBuilder();
        for (byte b : hash) {
            stringHash.append(String.format("%02X", b));
        }

        return stringHash.toString();
    }
}

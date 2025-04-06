package util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    private static final String KEY = "1234567890123456"; // 16자리 키
    private static final String IV = "abcdefghijklmnop";  // 16바이트 IV

    // Base64 방식 암호화
    public static String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // Base64 방식 복호화
    public static String decrypt(String encrypted, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] decoded = Base64.getDecoder().decode(encrypted);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    // Base64 방식 복호화 (고정된 키와 IV 사용)
    public static String decrypt(String encrypted) throws Exception {
        return decrypt(encrypted, KEY, IV);
    }

    // HEX 문자열을 byte 배열로 변환
    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                 + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    // HEX 인코딩된 AES 암호문 복호화
    public static String decryptFromHex(String hexEncrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] encryptedBytes = hexToBytes(hexEncrypted);
        byte[] decrypted = cipher.doFinal(encryptedBytes);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    // Base64인지 Hex인지 자동 판별 후 복호화
    public static String smartDecrypt(String encrypted) throws Exception {
        try {
            // Base64 디코딩 시도
            Base64.getDecoder().decode(encrypted);
            return decrypt(encrypted);
        } catch (IllegalArgumentException e) {
            // Base64 아님 => Hex로 간주
            return decryptFromHex(encrypted);
        }
    }
}
package zhibi.fast.commons.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

/**
 * 解密工具类
 *
 * @author 执笔
 * @date 2019/3/27 17:44
 */
public class DecryptUtils {
    /**
     * RSA 最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    /**
     * 公钥解密
     *
     * @param data         已加密数据
     * @param publicKey    公钥(BASE64编码)
     * @param keyAlgorithm 加密算法 RSA
     * @throws Exception
     */
    public static String decryptByPublic(String data, String publicKey, String keyAlgorithm) {
        PublicKey key = SignUtils.getPublicKey(publicKey, keyAlgorithm);
        try {
            return decrypt(data, keyAlgorithm, key);
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data         已加密数据
     * @param privateKey   私钥(BASE64编码)
     * @param keyAlgorithm 加密算法 RSA
     * @throws Exception
     */
    public static String decryptByPrivate(String data, String privateKey, String keyAlgorithm) {
        Key key = SignUtils.getPrivateKey(privateKey, keyAlgorithm);
        try {
            return decrypt(data, keyAlgorithm, key);
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     *
     * @param data         待解密数据
     * @param key
     * @param keyAlgorithm 加密算法 RSA
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private static String decrypt(String data, String keyAlgorithm, Key key) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher ci = Cipher.getInstance(keyAlgorithm);
        ci.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = Base64.getDecoder().decode(data);
        int inputLen = bytes.length;
        int offLen = 0;
        int i = 0;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (inputLen - offLen > 0) {
            byte[] cache;
            if (inputLen - offLen > MAX_DECRYPT_BLOCK) {
                cache = ci.doFinal(bytes, offLen, MAX_DECRYPT_BLOCK);
            } else {
                cache = ci.doFinal(bytes, offLen, inputLen - offLen);
            }
            byteArrayOutputStream.write(cache);
            i++;
            offLen = MAX_DECRYPT_BLOCK * i;
        }
        byteArrayOutputStream.close();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return new String(byteArray, StandardCharsets.UTF_8);
    }

}

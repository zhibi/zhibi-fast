package zhibi.fast.commons.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

/**
 * 加密工具
 *
 * @author 执笔
 * @date 2019/3/27 17:33
 */
public class EncryptUtils {

    /**
     * RSA 最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 使用私钥加密数据
     *
     * @param source       源数据
     * @param privateKey   私钥
     * @param keyAlgorithm 加密算法 RSA
     * @return
     */
    public static String encryptByPrivate(String source, String privateKey, String keyAlgorithm) {
        try {
            PrivateKey key = SignUtils.getPrivateKey(privateKey, keyAlgorithm);
            return encrypt(source, keyAlgorithm, key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用公钥加密数据
     *
     * @param source       源数据
     * @param publicKey    公钥(BASE64编码)
     * @param keyAlgorithm 加密算法 RSA
     */
    public static String encryptByPublic(String source, String publicKey, String keyAlgorithm) {
        try {
            PublicKey key = SignUtils.getPublicKey(publicKey, keyAlgorithm);
            return encrypt(source, keyAlgorithm, key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 加密数据
     *
     * @param source       源数据
     * @param keyAlgorithm 加密算法 RSA
     * @param key          密钥
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private static String encrypt(String source, String keyAlgorithm, Key key) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher ci = Cipher.getInstance(keyAlgorithm);
        ci.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = source.getBytes(StandardCharsets.UTF_8);
        int inputLen = bytes.length;
        int offLen = 0;//偏移量
        int i = 0;
        ByteArrayOutputStream bops = new ByteArrayOutputStream();
        while (inputLen - offLen > 0) {
            byte[] cache;
            if (inputLen - offLen > MAX_ENCRYPT_BLOCK) {
                cache = ci.doFinal(bytes, offLen, MAX_ENCRYPT_BLOCK);
            } else {
                cache = ci.doFinal(bytes, offLen, inputLen - offLen);
            }
            bops.write(cache);
            i++;
            offLen = MAX_ENCRYPT_BLOCK * i;
        }
        bops.close();
        byte[] encryptedData = bops.toByteArray();
        return Base64.getEncoder().encodeToString(encryptedData);
    }


}

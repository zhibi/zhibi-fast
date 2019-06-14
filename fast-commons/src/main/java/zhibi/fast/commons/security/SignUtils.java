package zhibi.fast.commons.security;

import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 签名工具类
 *
 * @author 执笔
 * @date 2019/3/27 17:00
 */
public class SignUtils {


    /**
     * 使用私钥签名
     *
     * @param privateKey   私钥
     * @param source       要签名的数据
     * @param algorithm    签名算法 SHA1withRSA
     * @param keyAlgorithm 加密算法 RSA
     * @return Base64 编码签名后的数据
     */
    public static String sign(String privateKey, String source, String algorithm, String keyAlgorithm) {
        try {
            /**
             * 签名算法
             */
            Signature sign = Signature.getInstance(algorithm);
            /**
             * 设置私钥
             */
            sign.initSign(SignUtils.getPrivateKey(privateKey, keyAlgorithm));
            /**
             * 设置明文
             */
            sign.update(source.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = sign.sign();
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encodeBuffer(bytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * md5 签名
     *
     * @param source
     * @return
     */
    public static String md5Sign(String source) {
        return DigestUtils.md5Hex(source.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 验签
     *
     * @param publicKey    公钥
     * @param source       要验签的数据
     * @param signStr      要验证的签名
     * @param algorithm    签名算法 SHA1withRSA
     * @param keyAlgorithm 加密算法 RSA
     * @return
     */
    public static boolean verifySign(String publicKey, String source, String signStr, String algorithm, String keyAlgorithm) {
        try {
            /**
             * 签名算法
             */
            Signature sign = Signature.getInstance(algorithm);
            /**
             * 设置公钥
             */
            sign.initVerify(getPublicKey(publicKey, keyAlgorithm));
            /**
             * 设置明文
             */
            sign.update(source.getBytes(StandardCharsets.UTF_8));
            BASE64Decoder decoder = new BASE64Decoder();
            return sign.verify(decoder.decodeBuffer(signStr));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转私钥
     *
     * @param privateKey 私钥
     * @param algorithm  加密算法
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey, String algorithm) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] keyBytes = decoder.decodeBuffer(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转公钥
     *
     * @param publicKey 公钥
     * @param algorithm 加密算法
     * @return
     */
    public static PublicKey getPublicKey(String publicKey, String algorithm) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] keyBytes = decoder.decodeBuffer(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePublic(x509KeySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}

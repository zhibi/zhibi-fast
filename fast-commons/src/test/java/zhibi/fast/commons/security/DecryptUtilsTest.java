package zhibi.fast.commons.security;

import org.junit.Test;

/**
 * @author 执笔
 * @date 2019/3/27 17:58
 */
public class DecryptUtilsTest {
    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQgEoj3z9JrdPNI23DbMQkl3gkGuDke7iBr5yrYyqolkTyxuBLWFwHNuGv4VKOj9fXg61QxpaJ/fxDBvMvmkBSRowHBloGFceVTx8wV/8u0DcjvTCu0IZ1zp6wjG6xBn5j66Sg/q+9hvaY2p7fkKmsvcW6VoNPgQHU1Cf01DLZmQIDAQAB+oXcINOiE3AsuZ4VJmwNZg9Y/7fY+OFRS2JAh5YMsrv2qyoGP+Z9ksre26NYR+Lt91B2lhdwJHLpQpziaANZm/ONb31fj/lwIDAQAB";
    public static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANCASiPfP0mt080jbcNsxCSXeCQa4OR7uIGvnKtjKqiWRPLG4EtYXAc24a/hUo6P19eDrVDGlon9/EMG8y+aQFJGjAcGWgYVx5VPHzBX/y7QNyO9MK7QhnXOnrCMbrEGfmPrpKD+r72G9pjant+Qqay9xbpWg0+BAdTUJ/TUMtmZAgMBAAECgYBSozY/Z4FW+31h5fPgK+DFu/8TGFAgXuTvCaJnz2Md9IkZTDejxT6cYWUr53toI5zhvz/XLw6FXNQ54KxMJq/s9PiZYUgq/PMrnyU4gBSTm5BmiWjdaGicVEZ1lofHjpkAchPNW/CzwxD8AeKI7QaObE+EkWbLAi6sa+nRdHKgrQJBAOwYLD2DncU15XCKS0RNzTrNohdBQcisOPHdtQO0CGZlxx3xjuU4WL6/EpdmbjTeYbOSDKCmY5vyVbYZdOWfEs8CQQDiFIwWpvW2WLxLVw3i2P55WmMMXuecwEzg++ae3Ht7nW0zNcWSsyvHh40sM8XqEzmWOzMY6JOePbkuVfWTc4cXAkBRzf5mQhiEoKwjVofF3v9hhKbJT/8vPR1uENgLtHHEqTdZFL3ihqeZUDNs6jz9bKCFy/E8KOsSueEg+6kZdwjZAkEAj2RW4fstd2VasDJb5ViaNqAEmJENOBej60L6KCJR07qqy0M8t+oaR2iLOtDvo6Jj8QxFQXQqRMCDVodAxjANKwJAL3KuaqA6kdy9RxdV3uP8nRXLY7C/1ZIK6U0pyZqKXEwpD+7Ar3hwwhPz9TeuoqjB/cCknZjw70BQFQ0/VUHW2g==";

    @Test
    public void testPrivate(){
        String rsa = EncryptUtils.encryptByPrivate("test jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihg", privateKey, "RSA");
        //System.out.println(rsa);
        System.out.println(DecryptUtils.decryptByPublic(rsa, publicKey, "RSA"));
        //System.out.println(DecryptUtils.decryptByPrivate(rsa, privateKey, "RSA"));
    }

    @Test
    public void testPublic(){
        String rsa = EncryptUtils.encryptByPublic("test jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihgtest jiojiogeiojigouihg", publicKey, "RSA");
        System.out.println(DecryptUtils.decryptByPrivate(rsa, privateKey, "RSA"));
    }
}
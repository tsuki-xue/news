package com.news.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;

import java.util.UUID;

/**
 * 密码加密工具类（基于 Shiro）
 */
public class PasswordUtils {

    // 加密算法名称
    public static final String HASH_ALGORITHM_NAME = "SHA-256";
    // 迭代次数（建议 1024 次以上）
    public static final int HASH_ITERATIONS = 1024;

    /**
     * 生成随机盐值（UUID 转 Base64 编码）
     */
    public static String generateSalt() {
        String uuid = UUID.randomUUID().toString();
        return Base64.encodeToString(uuid.getBytes());
    }

    /**
     * 加密密码（明文 + 盐值）
     * @param plainPassword 明文密码
     * @param salt          盐值
     * @return 加密后的十六进制字符串
     */
    public static String encryptPassword(String plainPassword, String salt) {
        return new Sha256Hash(
                plainPassword,          // 明文密码
                ByteSource.Util.bytes(salt), // 盐值（转换为字节源）
                HASH_ITERATIONS         // 迭代次数
        ).toHex();
    }

    /**
     * 验证密码是否正确
     * @param plainPassword 明文密码
     * @param encryptedPassword 加密后的密码
     * @param salt          盐值
     * @return true 匹配，false 不匹配
     */
    public static boolean validatePassword(String plainPassword, String encryptedPassword, String salt) {
        String newEncrypted = encryptPassword(plainPassword, salt);
        return newEncrypted.equals(encryptedPassword);
    }

    // 示例用法
    public static void main(String[] args) {
        // 生成盐值
//        String salt = generateSalt();
        String salt = "7a2d4f1e9b5c8d7e2f1a3b4c5d6e7f8a9b0c1d2e3f4a5b6c7d8e9f0a1b2c";
        System.out.println("盐值：" + salt);

        // 加密密码
        String encrypted = encryptPassword("123", salt);
        System.out.println("加密后密码：" + encrypted);

        // 验证密码
        boolean isValid = validatePassword("123", encrypted, salt);
        System.out.println("验证结果：" + isValid); // 输出 true
    }
}
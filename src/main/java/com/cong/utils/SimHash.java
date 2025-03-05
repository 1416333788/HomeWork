package com.cong.utils;

import com.hankcs.hanlp.HanLP;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

public class SimHash {

    /**
     * 计算字符串的 MD5 哈希值，并转换为二进制字符串
     *
     * @param str 输入字符串
     * @return 二进制哈希字符串
     */
    private static String getHash(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
            return new BigInteger(1, hashBytes).toString(2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 计算文本的 SimHash 值（128位二进制字符串）
     *
     * @param text 输入文本
     * @return SimHash 字符串
     */
    public static String getSimHash(String text) {
        if (text.length() < 10) { // 调整最低长度要求
            System.err.println("输入文本过短");
            return "";
        }

        int[] weight = new int[128];
        List<String> keywords = HanLP.extractKeyword(text, text.length());
        int size = keywords.size();

        for (int i = 0; i < size; i++) {
            String keywordHash = getHash(keywords.get(i));
            if (keywordHash.length() < 128) {
                keywordHash = String.format("%128s", keywordHash).replace(' ', '0');
            }
            for (int j = 0; j < 128; j++) {
                // 简化权重计算，正负权重各加1或减1
                weight[j] += (keywordHash.charAt(j) == '1') ? 1 : -1;
            }
        }

        StringBuilder simHash = new StringBuilder();
        for (int v : weight) {
            simHash.append(v > 0 ? '1' : '0');
        }
        return simHash.toString();
    }

    /**
     * 计算两个 SimHash 值之间的汉明距离
     *
     * @param hash1 SimHash 值1
     * @param hash2 SimHash 值2
     * @return 汉明距离
     */
    public static int getHammingDistance(String hash1, String hash2) {
        if (hash1.length() != hash2.length()) {
            System.err.println("SimHash 长度不匹配，无法计算汉明距离");
            return -1;
        }

        int distance = 0;
        for (int i = 0; i < hash1.length(); i++) {
            if (hash1.charAt(i) != hash2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    /**
     * 计算两个 SimHash 值的相似度（0到1之间的小数）
     *
     * @param hash1 SimHash 值1
     * @param hash2 SimHash 值2
     * @return 相似度
     */
    public static double getSimilarity(String hash1, String hash2) {
        int hammingDistance = getHammingDistance(hash1, hash2);
        if (hammingDistance < 0) {
            return 0.0;
        }
        return 1 - (hammingDistance / 128.0);
    }

    /**
     * 计算相似度，并返回格式为 "百分之XX.XX" 的字符串
     *
     * @param hash1 SimHash 值1
     * @param hash2 SimHash 值2
     * @return 格式化的相似度字符串，例如 "百分之98.12"
     */
    public static String getSimilarityPercentage(String hash1, String hash2) {
        double similarity = getSimilarity(hash1, hash2);
        double percentage = similarity * 100;
        return "百分之" + String.format("%.2f", percentage);
    }
}

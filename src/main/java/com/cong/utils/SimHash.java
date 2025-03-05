package com.cong.utils;

import com.hankcs.hanlp.HanLP;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

public class SimHash {

    // 计算字符串的 MD5 哈希值，并转换为 128 位二进制字符串
    public static String getHash(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
            return new BigInteger(1, hashBytes).toString(2); // 二进制格式返回
        } catch (Exception e) {
            e.printStackTrace();
            return str;  // 出错时返回原始字符串
        }
    }

    // 计算 SimHash
    public static String getSimHash(String text) {
        if (text.length() < 66) {
            System.out.println("输入文本过短");
            return "";
        }

        int[] weight = new int[128]; // 128 位权重数组
        List<String> keywords = HanLP.extractKeyword(text, text.length()); // 提取关键词
        int size = keywords.size();

        for (int i = 0; i < size; i++) {
            String keyword = keywords.get(i);
            String hash = getHash(keyword);

            // 补全到 128 位
            StringBuilder hashBuilder = new StringBuilder(hash);
            while (hashBuilder.length() < 128) {
                hashBuilder.append("0");
            }
            hash = hashBuilder.toString();

            // 更新权重
            for (int j = 0; j < weight.length; j++) {
                weight[j] += (hash.charAt(j) == '1' ? 1 : -1);
            }
        }

        // 生成最终 SimHash 值
        StringBuilder simHash = new StringBuilder();
        for (int w : weight) {
            simHash.append(w > 0 ? "1" : "0");
        }
        return simHash.toString();
    }

    // 计算两个 SimHash 之间的海明距离
    public static int getHammingDistance(String simHash1, String simHash2) {
        if (simHash1.length() != simHash2.length()) {
            return -1;
        }
        int distance = 0;
        for (int i = 0; i < simHash1.length(); i++) {
            if (simHash1.charAt(i) != simHash2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    // 计算相似度（基于海明距离）
    private static double getSimilarity(int distance) {
        return 1.0 - distance / 128.0;
    }

    // 计算两个文本的相似度
    public static double getSimilarity(String text1, String text2) {
        String simHash1 = getSimHash(text1);
        String simHash2 = getSimHash(text2);

        if (simHash1.isEmpty() || simHash2.isEmpty()) {
            return 0.0;
        }

        int hammingDistance = getHammingDistance(simHash1, simHash2);
        return getSimilarity(hammingDistance);
    }
}

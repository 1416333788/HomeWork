package com.cong;

import com.cong.utils.FileUtils;
import com.cong.utils.SimHash;

public class Main {
    public static void main(String[] args) {
        // 指定原文件路径和待比较文件路径
        String originalPath = "src/test/java/orig.txt";
        String[] candidatePaths = {
                "src/test/java/orig_0.8_add.txt",
                "src/test/java/orig_0.8_del.txt",
                "src/test/java/orig_0.8_dis_1.txt",
                "src/test/java/orig_0.8_dis_10.txt",
                "src/test/java/orig_0.8_dis_15.txt"
        };

        // 读取原文件内容
        String originalText = FileUtils.readTxt(originalPath);
        if (originalText.isEmpty()) {
            System.err.println("原文件内容为空！");
            return;
        }
        String origHash = SimHash.getSimHash(originalText);
        System.out.println("Original SimHash: " + origHash);

        // 对每个候选文件进行比较，并输出相似度百分比
        for (String candidatePath : candidatePaths) {
            String candidateText = FileUtils.readTxt(candidatePath);
            if (candidateText.isEmpty()) {
                System.err.println("文件 " + candidatePath + " 内容为空！");
                continue;
            }
            String candidateHash = SimHash.getSimHash(candidateText);

            // 计算相似度和格式化输出
            double similarity = SimHash.getSimilarity(origHash, candidateHash);
            String simPercent = SimHash.getSimilarityPercentage(origHash, candidateHash);

            System.out.println("Similarity between orig.txt and " + candidatePath + " : " + simPercent);
        }


    }
}

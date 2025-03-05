package com.cong;

import com.cong.utils.FileUtils;
import com.cong.utils.SimHash;

public class Main {
    public static void main(String[] args) {
        // 要求至少两个参数：第一个为原文件，其余为候选文件
        if (args.length < 2) {
            System.err.println("Usage: java -jar SoftwareHomework.jar <original_file> <candidate_file1> [candidate_file2 ...]");
            return;
        }

        // 第一个参数为原文件路径
        String originalFile = args[0];
        String originalText = FileUtils.readTxt(originalFile);
        if (originalText.isEmpty()) {
            System.err.println("Error: 原文件内容为空或无法读取：" + originalFile);
            return;
        }

        // 计算原文件的 SimHash
        String origHash = SimHash.getSimHash(originalText);
        System.out.println("原文件 SimHash: " + origHash);

        // 循环处理后续候选文件
        for (int i = 1; i < args.length; i++) {
            String candidateFile = args[i];
            String candidateText = FileUtils.readTxt(candidateFile);
            if (candidateText.isEmpty()) {
                System.err.println("Warning: 候选文件内容为空或无法读取：" + candidateFile);
                continue;
            }

            // 计算候选文件的 SimHash
            String candidateHash = SimHash.getSimHash(candidateText);

            // 计算相似度，返回格式为 "百分之XX.XX"
            String simPercentage = SimHash.getSimilarityPercentage(origHash, candidateHash);
            System.out.println("原文件与 " + candidateFile + " 的相似度: " + simPercentage);
        }
    }
}

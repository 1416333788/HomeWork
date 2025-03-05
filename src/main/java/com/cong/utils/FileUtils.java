package com.cong.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    // 读取文件内容
    public static String readTxt(String txtPath) {
        StringBuilder content = new StringBuilder();
        File file = new File(txtPath);

        // 如果文件不存在，直接返回空字符串
        if (!file.exists()) {
            return "";
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }


    // 写入内容到文件
    public static void writeTxt(String content, String txtPath) {
        try (FileWriter writer = new FileWriter(txtPath, true)) {
            writer.write(content + "\r\n"); // 写入并换行
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

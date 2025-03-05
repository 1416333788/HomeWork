import com.cong.utils.FileUtils;
import com.cong.utils.SimHash;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class SimHashTest {

    @Test
    public void testFileSimilarities() {
        // 定义原文件路径和待测文件路径（请根据实际情况修改路径）
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
        assertFalse(originalText.isEmpty(), "原文件内容为空！");
        String origHash = SimHash.getSimHash(originalText);
        System.out.println("Original SimHash: " + origHash);

        // 对每个待测试文件进行比较，并输出相似度百分比
        for (String candidatePath : candidatePaths) {
            String candidateText = FileUtils.readTxt(candidatePath);
            assertFalse(candidateText.isEmpty(), "文件 " + candidatePath + " 内容为空！");
            String candidateHash = SimHash.getSimHash(candidateText);

            // 计算相似度及格式化输出
            double similarity = SimHash.getSimilarity(origHash, candidateHash);
            String simPercent = SimHash.getSimilarityPercentage(origHash, candidateHash);

            System.out.println("Similarity between orig.txt and " + candidatePath + " : " + simPercent);

            // 可选：如果你有预期值，可以使用断言验证相似度范围
            // 例如 assertTrue(similarity >= 0 && similarity <= 1);
        }
    }
}


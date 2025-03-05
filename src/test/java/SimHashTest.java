import com.cong.utils.SimHash;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SimHashTest {

    // 测试有效文本能生成 128 位的 SimHash 值
    @Test
    public void testGetSimHash_NonEmptyForValidText() {
        String text = "今天是星期天，天气晴朗，我去看电影。";
        String simHash = SimHash.getSimHash(text);
        assertNotEquals("", simHash);
        assertEquals(128, simHash.length());
    }

    // 测试文本过短时返回空 SimHash
    @Test
    public void testGetSimHash_EmptyForShortText() {
        String text = "短文";
        String simHash = SimHash.getSimHash(text);
        assertEquals("", simHash);
    }

    // 测试相同哈希值之间汉明距离为 0
    @Test
    public void testGetHammingDistance_SameHashes() {
        String identical = "1".repeat(128);
        int distance = SimHash.getHammingDistance(identical, identical);
        assertEquals(0, distance);
    }

    // 测试完全不同的哈希值汉明距离为 128
    @Test
    public void testGetHammingDistance_DifferentHashes() {
        String hash1 = "1".repeat(128);
        String hash2 = "0".repeat(128);
        int distance = SimHash.getHammingDistance(hash1, hash2);
        assertEquals(128, distance);
    }

    // 测试相同哈希值的相似度为 1.0
    @Test
    public void testGetSimilarity_SameHashes() {
        String hash = "1".repeat(128);
        double similarity = SimHash.getSimilarity(hash, hash);
        assertEquals(1.0, similarity, 0.0001);
    }

    // 测试 getSimilarityPercentage 返回格式正确的字符串
    @Test
    public void testGetSimilarityPercentage_Format() {
        String hash1 = "1".repeat(128);
        String hash2 = "0".repeat(128);
        String percent = SimHash.getSimilarityPercentage(hash1, hash2);
        // 相似度为 0 时应返回 "百分之0.00"
        assertEquals("百分之0.00", percent);
    }
}

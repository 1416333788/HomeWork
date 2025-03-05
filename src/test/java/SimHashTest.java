import com.cong.utils.SimHash;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimHashTest {

    @Test
    public void testSimHash() {
        String text1 = "今天是星期天，天气晴朗，我去看电影。";
        String text2 = "今天是周天，天气晴，我晚上要去看电影。";

        String hash1 = SimHash.getSimHash(text1);
        String hash2 = SimHash.getSimHash(text2);

        // 打印查看 SimHash 值
        System.out.println("SimHash 1: " + hash1);
        System.out.println("SimHash 2: " + hash2);

        // 测试 SimHash 是否有效
        assertNotNull(hash1);
        assertNotNull(hash2);

        // 测试计算相似度
        int distance = SimHash.getHammingDistance(hash1, hash2);
        assertTrue(distance >= 0); // 汉明距离不能为负

        double similarity = SimHash.getSimilarity(hash1, hash2);
        System.out.println("相似度：" + similarity);

        // 断言相似度是否在预期范围内
        assertTrue(similarity >= 0 && similarity <= 1);
    }
}

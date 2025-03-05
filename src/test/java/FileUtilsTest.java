import com.cong.utils.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUtilsTest {

    private final String testFilePath = "testFile.txt";

    @BeforeEach
    void setup() throws IOException {
        Files.deleteIfExists(Path.of(testFilePath)); // 确保每次测试前文件不存在
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(testFilePath)); // 确保测试后清理文件
    }

    @Test
    public void testReadTxt_ValidFile() throws IOException {
        Files.writeString(Path.of(testFilePath), "Hello World!", StandardCharsets.UTF_8);
        String content = FileUtils.readTxt(testFilePath);
        assertEquals("Hello World!", content, "文件内容应与预期值匹配");
    }

    @Test
    public void testReadTxt_FileNotExist() {
        String content = FileUtils.readTxt("nonexistent.txt");
        assertEquals("", content, "不存在的文件应返回空字符串");
    }

    @Test
    public void testReadTxt_MultiLine() throws IOException {
        Files.writeString(Path.of(testFilePath), "Line1\nLine2\nLine3", StandardCharsets.UTF_8);
        String content = FileUtils.readTxt(testFilePath);
        assertEquals("Line1Line2Line3", content.replace("\r", ""), "多行内容应被拼接成一行");
    }

    @Test
    public void testReadTxt_UTF8Characters() throws IOException {
        Files.writeString(Path.of(testFilePath), "你好，世界！", StandardCharsets.UTF_8);
        String content = FileUtils.readTxt(testFilePath);
        assertEquals("你好，世界！", content, "UTF-8 编码的内容应被正确读取");
    }

    @Test
    public void testWriteTxt_AppendsContent() throws IOException {
        FileUtils.writeTxt("First Line", testFilePath);
        FileUtils.writeTxt("Second Line", testFilePath);

        String content = Files.readString(Path.of(testFilePath), StandardCharsets.UTF_8);
        assertTrue(content.contains("First Line"), "文件应包含第一行内容");
        assertTrue(content.contains("Second Line"), "文件应包含第二行内容");
    }
}
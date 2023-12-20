package pairproject.foodmap.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FileUtilTest {
    @InjectMocks
    private FileUtil fileUtil;

    @Test
    @DisplayName("확장자 추출 테스트")
    public void extensionExtractTest() {
        //given
        String filename = "test.xlsx";
        String onlyExtension = ".xlsx";

        //when
        String extension = fileUtil.extractExtension(filename);

        //then
        assertEquals(extension, onlyExtension);
    }

    @Test
    @DisplayName("UUID로 파일명 변경 테스트")
    public void UUIDFilenameChangeTest() {
        //given
        String filename = "test.xlsx";
        String extension = ".xlsx";

        //when
        String newFilename = fileUtil.changeFilename(filename);

        //then
        assertNotEquals(newFilename, filename);
        assertTrue(newFilename.endsWith(extension));
    }

    @Test
    @DisplayName("메인 이미지 파일 하나 저장")
    public void mainImage_OneFileSave() {
        //given
        String filename = "file.txt";
        MultipartFile mf = new MockMultipartFile("file", filename, "text/plain", "file data".getBytes());
        boolean checkMainImage = true;

        //when
        String newFilename = fileUtil.saveOne(mf, checkMainImage);

        //then
        assertNotEquals(filename, newFilename);
        assertTrue(newFilename.startsWith("Main_"));
    }

    @Test
    @DisplayName("메인 이미지 아닐 때 파일 하나 저장")
    public void noMainImage_OneFileSave() {
        //given
        String filename = "file.txt";
        MultipartFile mf = new MockMultipartFile("file", filename, "text/plain", "file data".getBytes());
        boolean checkMainImage = false;

        //when
        String newFilename = fileUtil.saveOne(mf, checkMainImage);

        //then
        assertNotEquals(filename, newFilename);
        assertFalse(newFilename.startsWith("Main_"));
    }

    @Test
    @DisplayName("이미지 파일 여러개 저장 테스트")
    public void ImageFilesSaveTest() {
        //given
        String filename1 = "file1.txt";
        String filename2 = "file2.txt";
        MultipartFile mf1 = new MockMultipartFile("file1", filename1, "text/plain", "file1 data".getBytes());
        MultipartFile mf2 = new MockMultipartFile("file2", filename2, "text/plain", "file2 data".getBytes());
        List<MultipartFile> files = List.of(mf1, mf2);
        List<String> filenames = List.of(filename1, filename2);

        //when
        List<String> newFilenames = fileUtil.saveAll(files);

        //then
        assertEquals(newFilenames.size(), filenames.size());
    }
}

package pairproject.foodmap.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pairproject.foodmap.domain.BoardImage;
import pairproject.foodmap.repository.BoardImageMapper;
import pairproject.foodmap.util.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardImageServiceTest {
    @Captor
    ArgumentCaptor<List<String>> filenamesCaptor;
    @InjectMocks
    private BoardImageService boardImageService;
    @Mock
    private BoardImageMapper boardImageMapper;
    @Mock
    private FileUtil fileUtil;

    @Test
    @DisplayName("등록 이미지가 없을 때 빈 리스트 반환")
    public void noFiles_ReturnEmpty() {
        //given
        long boardId = 1L;

        //when
        List<BoardImage> boardImages =
                boardImageService.createBoardImage(boardId, null, null);

        //then
        assertEquals(boardImages.size(), 0);
        verifyNoInteractions(fileUtil);
        verifyNoInteractions(boardImageMapper);
    }

    @Test
    @DisplayName("메인 이미지가 없을 때 mainImageFile 설정 후 리스트 반환")
    public void noMainFile_ReturnBoardImageList() {
        //given
        long boardId = 1L;
        List<MultipartFile> addFiles = new ArrayList<>();
        addFiles.add(mock(MultipartFile.class));

        List<BoardImage> boardImages = new ArrayList<>();
        IntStream.range(0, 5)
                .forEach(i -> boardImages.add(new BoardImage()));

        MultipartFile emptyFile = new MockMultipartFile("emptyFile", new byte[0]);
        when(boardImageService.getBoardImageAllByBoardId(boardId)).thenReturn(boardImages);

        //when
        List<BoardImage> newBoardImages = boardImageService.createBoardImage(boardId, addFiles, emptyFile);

        //then
        assertEquals(newBoardImages.size(), 5);

    }

    @Test
    @DisplayName("메인 이미지, 추가 이미지 사진이 있을 때 리스트 반환")
    public void ReturnBoardImageList() {
        //given
        long boardId = 1L;
        MultipartFile mainImageFile = mock(MultipartFile.class);
        List<MultipartFile> addFiles = new ArrayList<>();
        addFiles.add(mock(MultipartFile.class));

        List<BoardImage> boardImages = new ArrayList<>();
        IntStream.range(0, 3)
                .forEach(i -> boardImages.add(new BoardImage()));
        when(boardImageService.getBoardImageAllByBoardId(boardId)).thenReturn(boardImages);

        //when
        List<BoardImage> newBoardImages = boardImageService.createBoardImage(boardId, addFiles, mainImageFile);

        //then
        assertEquals(newBoardImages.size(), 3);
        verify(fileUtil).saveOne(mainImageFile, true);

    }

    @Test
    @DisplayName("이미지 파일 저장과 DB 저장 테스트")
    public void saveFileAndDBTest() {
        //given
        long boardId = 1L;
        MultipartFile mainImageFile = mock(MultipartFile.class);
        List<MultipartFile> addFiles = new ArrayList<>();
        addFiles.add(mock(MultipartFile.class));

        String saveOne = "sample.jpg";
        List<String> saveFilenames = new ArrayList<>(Arrays.asList("1.jpg", "2.jpg"));
        when(fileUtil.saveOne(mainImageFile, true)).thenReturn(saveOne);
        when(boardImageService.saveFilesAndGetFilenames(boardId, addFiles)).thenReturn(saveFilenames);

        //when
        boardImageService.saveFilesAndDB(addFiles, mainImageFile, boardId);

        //then
        saveFilenames.add(saveOne);
        verify(boardImageMapper, times(1)).save(saveFilenames, boardId);
    }


    @Test
    @DisplayName("addFiles 만 있는 경우 게시글 이미지 변경 테스트")
    public void onlyAddFilesNotNull_BoardImageEditTest() {
        //given
        long boardId = 1L;
        List<MultipartFile> addFiles = List.of(mock(MultipartFile.class), mock(MultipartFile.class));
        List<String> deleteFilenames = null;
        List<BoardImage> boardImages = new ArrayList<>();
        IntStream.range(0, 3)
                .forEach(i -> boardImages.add(new BoardImage()));
        when(boardImageService.getBoardImageAllByBoardId(boardId)).thenReturn(boardImages);

        //when
        List<BoardImage> newBoardImages = boardImageService.updateBoardImage(boardId, addFiles, deleteFilenames);

        //then
        verify(fileUtil, times(1)).saveAll(addFiles);
        verify(boardImageMapper, never()).delete(deleteFilenames);
        assertEquals(newBoardImages.size(), boardImages.size());
    }

    @Test
    @DisplayName("deleteFiles 만 있는 경우 이미지 변경 테스트")
    public void onlyDeleteFilesNotNull_BoardImageEditTest() {
        //given
        long boardId = 1L;
        List<MultipartFile> addFiles = null;
        List<String> deleteFilenames = new ArrayList<>();
        List<BoardImage> boardImages = new ArrayList<>();
        IntStream.range(0, 5)
                .forEach(i -> deleteFilenames.add(UUID.randomUUID() + ".png"));
        IntStream.range(0, 3)
                .forEach(i -> boardImages.add(new BoardImage()));
        when(boardImageService.getBoardImageAllByBoardId(boardId)).thenReturn(boardImages);

        //when
        List<BoardImage> newBoardImages = boardImageService.updateBoardImage(boardId, addFiles, deleteFilenames);

        //then
        verify(fileUtil, never()).saveAll(addFiles);
        verify(fileUtil, times(1)).deleteAll(deleteFilenames);
        verify(boardImageMapper, times(1)).delete(deleteFilenames);
        assertEquals(newBoardImages.size(), boardImages.size());
    }


    @Test
    @DisplayName("add, delete 파일 모두 있는 경우 게시글 이미지 변경 테스트")
    public void boardImageEditTest() {
        //given
        long boardId = 1L;
        List<MultipartFile> addFiles = List.of(mock(MultipartFile.class), mock(MultipartFile.class));
        List<String> deleteFilenames = new ArrayList<>();
        List<BoardImage> boardImages = new ArrayList<>();
        IntStream.range(0, 5)
                .forEach(i -> deleteFilenames.add(UUID.randomUUID() + ".png"));
        IntStream.range(0, 3)
                .forEach(i -> boardImages.add(new BoardImage()));
        when(boardImageService.getBoardImageAllByBoardId(boardId)).thenReturn(boardImages);

        //when
        List<BoardImage> newBoardImages = boardImageService.updateBoardImage(boardId, addFiles, deleteFilenames);

        //then
        verify(fileUtil, times(1)).saveAll(addFiles);
        verify(boardImageMapper, times(1)).delete(deleteFilenames);
        assertEquals(newBoardImages.size(), boardImages.size());
    }
    @Test
    @DisplayName("게시글 아이디(pk)에 따른 게시글 이미지 전체 목록 테스트")
    public void boardImageListByBoardIdTest() {
        //given
        long boardId = 1L;
        List<BoardImage> boardImages = new ArrayList<>();
        IntStream.range(0, 6)
                .forEach(i -> boardImages.add(new BoardImage()));
        when(boardImageMapper.findAllByBoardId(boardId)).thenReturn(boardImages);

        //when
        List<BoardImage> newBoardImages = boardImageService.getBoardImageAllByBoardId(boardId);

        //then
        verify(boardImageMapper, times(1)).findAllByBoardId(boardId);
        assertEquals(newBoardImages.size(), boardImages.size());

    }

    @Test
    @DisplayName("게시글 이미지 삭제 테스트")
    public void boardImageDeleteTest() {
        //given
        List<String> filenames = List.of("1.png");

        //when
        boardImageMapper.delete(filenames);

        //then
        verify(boardImageMapper).delete(filenamesCaptor.capture());
        assertEquals(filenamesCaptor.getAllValues().size(), filenames.size());
    }

}

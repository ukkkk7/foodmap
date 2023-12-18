package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pairproject.foodmap.domain.BoardImage;
import pairproject.foodmap.repository.BoardImageMapper;
import pairproject.foodmap.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardImageService {
    @Value("${file.image.path}")
    private String fileDir;
    private final BoardImageMapper boardImageMapper;
    private final FileUtil fileUtil;

    public List<BoardImage> createBoardImage(long boardId,
                                             List<MultipartFile> addFiles,
                                             MultipartFile mainImageFile) {

        if (addFiles != null || mainImageFile != null) {
            //사용자가 mainImageFile 를 선택(생성)했는지 확인
            if (mainImageFile.isEmpty()) {
                mainImageFile = addFiles.get(0);
                addFiles.remove(0);
            }
            saveFilesAndDB(addFiles, mainImageFile, boardId);
            return getBoardImageAllByBoardId(boardId);
        }
        return new ArrayList<>();
    }

    private void saveFilesAndDB(List<MultipartFile> addFiles, MultipartFile mainImageFile, long boardId) {
        String savedOne = fileUtil.saveOne(mainImageFile, true);//mainImageFile 저장
        List<String> saveFilenames = saveFilesAndGetFilenames(boardId, addFiles);
        saveFilenames.add(savedOne);
        boardImageMapper.save(saveFilenames, boardId);
    }

    public List<BoardImage> updateBoardImage(long boardId,
                                             List<MultipartFile> addFiles,
                                             List<String> deleteFilenames) {
        if (addFiles != null) {
            List<String> saveFilenames = saveFilesAndGetFilenames(boardId, addFiles);
            boardImageMapper.save(saveFilenames, boardId);
        }

        if (deleteFilenames != null) {
            fileUtil.deleteAll(deleteFilenames);
            boardImageMapper.delete(deleteFilenames);
        }
        return getBoardImageAllByBoardId(boardId);
    }

    private List<String> saveFilesAndGetFilenames(long boardId, List<MultipartFile> addFiles) {
        fileUtil.updateFileDir(fileDir);
        return fileUtil.saveAll(addFiles);

    }

    public List<BoardImage> getBoardImageAllByBoardId(long boardId) {
        return boardImageMapper.findAllByBoardId(boardId);
    }

    public List<String> getFilenameByBoardId(long boardId) {
        List<BoardImage> boardImages = getBoardImageAllByBoardId(boardId);
        return boardImages.stream()
                .map(BoardImage::getName)
                .toList();
    }

    public void deleteBoardImage(List<String> filenames) {
        boardImageMapper.delete(filenames);
    }

    public List<BoardImage> getBoardMainImageAll(List<Long> boardIdAll) {
        List<BoardImage> boardMainImages = new ArrayList<>();
        for (Long boardId : boardIdAll) {
            BoardImage boardImage = getMainImageFilename(boardId);
            boardMainImages.add(boardImage);
        }
        return boardMainImages;
    }

    private BoardImage getMainImageFilename(Long boardId) {
        return boardImageMapper.findMainImage(boardId);
    }

}

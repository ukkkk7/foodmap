package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pairproject.foodmap.domain.BoardImage;
import pairproject.foodmap.repository.BoardImageMapper;
import pairproject.foodmap.util.FileUtil;

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
        //사용자가 mainImageFile 를 선택(생성)했는지 확인
        if (mainImageFile.isEmpty()) {
            mainImageFile = addFiles.get(0);
            addFiles.remove(0);
        }
        fileUtil.updateFileDir(fileDir); //파일경로 변경
        String savedOne = fileUtil.saveOne(mainImageFile, true);//mainImageFile 저장
        List<String> filenames = fileUtil.saveAll(addFiles); //나머지 이미지 파일 저장
        filenames.add(savedOne);

        boardImageMapper.save(filenames, boardId); //DB 저장
        return getBoardImageAllByBoardId(boardId);
    }

    public List<BoardImage> updateBoardImage(long boardId,
                                             List<MultipartFile> addFiles,
                                             List<String> deleteFilenames) {
        fileUtil.updateFileDir(fileDir);
        fileUtil.deleteAll(deleteFilenames);
        List<String> addFilenames = fileUtil.saveAll(addFiles);

        boardImageMapper.save(addFilenames, boardId);
        boardImageMapper.delete(deleteFilenames);
        return getBoardImageAllByBoardId(boardId);
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
}

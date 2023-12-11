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
                                             List<MultipartFile> multipartFiles,
                                             MultipartFile mainImageFile) {
        //사용자가 mainImageFile 를 선택(생성)했는지 확인
        if (mainImageFile.isEmpty()) {
            mainImageFile = multipartFiles.get(0);
            multipartFiles.remove(0);
        }
        fileUtil.updateFileDir(fileDir); //파일경로 변경
        String savedOne = fileUtil.saveOne(mainImageFile, true);//mainImageFile 저장
        List<String> filenames = fileUtil.saveAll(multipartFiles); //나머지 이미지 파일 저장
        filenames.add(savedOne);

        boardImageMapper.save(filenames, boardId); //DB 저장
        return boardImageMapper.findAllByBoardId(boardId);
    }
}

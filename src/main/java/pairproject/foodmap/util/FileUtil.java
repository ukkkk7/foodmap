package pairproject.foodmap.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class FileUtil {
    private String fileDir;

    public void updateFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String extractExtension(String filename) {
        int lastIndex = filename.lastIndexOf(".");
        return filename.substring(lastIndex);
    }

    public String changeFilename(String filename) {
        String extension = extractExtension(filename);
        return UUID.randomUUID().toString() + extension;
    }

    public String saveOne(MultipartFile multipartFile, boolean checkMainImage) {
        String filename = changeFilename(multipartFile.getOriginalFilename());
        //사용자가 선택한 메인 이미지의 경우 파일명에 'Main_' 추가
        String changeFilename = checkMainImage ? "Main_" + filename : filename;
        File file = new File(fileDir + changeFilename);

        try {
            multipartFile.transferTo(file);
            log.info("{} 파일 저장 성공", changeFilename);
        } catch (Exception e) {
            deleteOne(changeFilename); //남아있는 파일이 있는 경우 삭제
            log.error("[Exception] {}", e.getMessage());
            throw new RuntimeException("파일 저장 실패");
        }
        return changeFilename;

    }

    public List<String> saveAll(List<MultipartFile> multipartFiles) {
        List<String> filenames = new ArrayList<>();
        try {
            //로컬에 파일 저장, UUID 로 변경한 파일명 리스트 생성
            for (MultipartFile multipartFile : multipartFiles) {
                String filename = saveOne(multipartFile, false);
                filenames.add(filename);
            }
        } catch (Exception e) { //실패하는 경우 저장했던 모든 파일 삭제
            deleteAll(multipartFiles
                    .stream()
                    .map(MultipartFile::getName).toList());
            log.error("[Exception] {}", e.getMessage());
            throw new RuntimeException();
        }
        return filenames;
    }

    public void deleteOne(String filename) {
        File file = new File(fileDir + filename);
        if (file.exists()) {
            if (file.delete()) {
                log.info("{} 파일 삭제 성공", filename);
            } else {
                log.error("{} 파일 삭제 실패", filename);
                throw new RuntimeException("파일 삭제 실패");
            }
        } else {
            log.info("{} 파일이 존재하지 않습니다.", filename);
        }
    }

    public void deleteAll(List<String> filenames) {
        for (String filename : filenames) {
            deleteOne(filename);
        }
    }
}

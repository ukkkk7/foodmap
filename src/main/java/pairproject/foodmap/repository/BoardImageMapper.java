package pairproject.foodmap.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pairproject.foodmap.domain.BoardImage;

import java.util.List;

@Mapper
@Repository
public interface BoardImageMapper {

    void save(@Param("filenames") List<String> filenames, @Param("boardId") long boardId);
    List<BoardImage> findAllByBoardId(long boardId);
    void delete(List<String> filenames);
    BoardImage findMainImage(Long boardId);
}

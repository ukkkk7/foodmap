package pairproject.foodmap.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pairproject.foodmap.domain.BoardImage;

import java.util.List;

@Mapper
public interface BoardImageMapper {

    void save(@Param("filenames") List<String> filenames, @Param("boardId") long boardId);
    List<BoardImage> findAllByBoardId(long boardId);


}

package pairproject.foodmap.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pairproject.foodmap.domain.Board;

import java.util.List;

@Mapper
@Repository
public interface BoardMapper {
    void saveAndGetId(Board board);
    Board findById(long boardId);
    List<Board> findAll();
    int edit(@Param("board") Board board, @Param("boardId") long boardId);
    int deleteById(long boardId);
    List<Long> findBoardIdByStoreId(long storeId);
}

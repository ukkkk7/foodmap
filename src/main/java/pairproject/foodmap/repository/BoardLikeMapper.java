package pairproject.foodmap.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BoardLikeMapper {
    void save(@Param("userId") long userId, @Param("boardId") long boardId);

    void delete(@Param("userId") long userId, @Param("boardId") long boardId);

    List<Long> findBoardIdAllByUserId(long userId);
}

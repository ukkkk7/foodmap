package pairproject.foodmap.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pairproject.foodmap.domain.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    void save(Category category);

    void deleteById(long categoryId);

    List<String> findNameAll();

    void edit(@Param("categoryId") long categoryId,
              @Param("category") Category category);
}

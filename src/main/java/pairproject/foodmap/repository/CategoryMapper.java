package pairproject.foodmap.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pairproject.foodmap.domain.Category;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {

    void save(Category category);

    void deleteById(String categoryId);

    List<Category> findAll();

    void edit(@Param("categoryId") String categoryId,
              @Param("newCategoryId") String newCategoryId);
}

package pairproject.foodmap.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pairproject.foodmap.domain.Grade;
import pairproject.foodmap.domain.User;

@Mapper
@Repository
public interface UserMapper {

    void editUserGrade(@Param("grade") Grade grade, @Param("userId") long userId);

    User findUserById(long userId);

    void save(User user);
}

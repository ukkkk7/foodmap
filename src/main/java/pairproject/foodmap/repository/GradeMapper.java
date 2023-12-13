package pairproject.foodmap.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import pairproject.foodmap.domain.Grade;

@Mapper
@Repository
public interface GradeMapper {
    void save(Grade grade);

    void deleteById(long gradeId);

    Integer findGradeIdByRange(int followerCount);
}

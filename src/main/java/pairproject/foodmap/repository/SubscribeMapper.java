package pairproject.foodmap.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SubscribeMapper {

    void save(@Param("following") long following,
              @Param("follower") long follower);

    void deleteById(long subscribeId);
}

package pairproject.foodmap.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubscribeMapper {

    void save(@Param("following") long following,
              @Param("follower") long follower);

    void deleteById(long subscribeId);

    Integer findFollowerCount(long userId);

    List<Long> findFollowingList(long userId);

    List<Long> findFollowerList(long userId);
}

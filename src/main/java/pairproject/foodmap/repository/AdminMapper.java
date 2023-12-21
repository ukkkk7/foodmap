package pairproject.foodmap.repository;

import org.apache.ibatis.annotations.Mapper;
import pairproject.foodmap.dto.UserDto;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<UserDto> getUserList();

    void deleteUser(long id);

    List<UserDto> searchUser(String name);

}

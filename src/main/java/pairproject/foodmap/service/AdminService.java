package pairproject.foodmap.service;

import org.springframework.stereotype.Service;
import pairproject.foodmap.dto.UserDto;
import pairproject.foodmap.repository.AdminMapper;

import java.util.List;

@Service
public class AdminService {

    private final AdminMapper adminMapper;

    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public List<UserDto> getUserList() {
      return adminMapper.getUserList();
    }


    public void deleteUser(long id) {
        adminMapper.deleteUser(id);
    }

    public List<UserDto> searchUser(String name) {
       return adminMapper.searchUser(name);
    }
}

package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pairproject.foodmap.domain.Grade;
import pairproject.foodmap.domain.User;
import pairproject.foodmap.repository.UserMapper;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public void updateUserGrade(Grade grade, long userId) { //등급 변경
        User user = getUserById(userId);
        user.updateGrade(grade);
        userMapper.editUserGrade(grade, userId);
    }

    public void createUser(User user) {
        userMapper.save(user);
    }

    public User getUserById(long userId) {
        return userMapper.findUserById(userId);
    }
}

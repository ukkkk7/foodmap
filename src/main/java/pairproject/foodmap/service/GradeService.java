package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pairproject.foodmap.domain.Grade;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GradeService {

    private final UserService userService;

    public void updateGrade(int followerCount, long userId) {
        Grade grade;

        if (followerCount < 100) {
            return;
        } else if (followerCount < 300) {
            grade = Grade.LEV2;
        } else if (followerCount < 500) {
            grade = Grade.LEV3;
        } else if (followerCount < 1000) {
            grade = Grade.LEV4;
        } else {
            grade = Grade.LEV5;
        }

        userService.updateUserGrade(grade, userId);
        log.trace("{} 님의 등급이 {} 으로 업데이트 되었습니다. ", userId, grade.getName());
    }
}


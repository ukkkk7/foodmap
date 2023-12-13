package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pairproject.foodmap.domain.Grade;
import pairproject.foodmap.repository.GradeMapper;

@Service
@Transactional
@RequiredArgsConstructor
public class GradeService {

    private final GradeMapper gradeMapper;
    private final UserService userService;
    public void updateGrade(int followerCount, long userId) {
        int gradeId = gradeMapper.findGradeIdByRange(followerCount);
        userService.updateGrade(gradeId, userId);
        System.out.println(userId + "님, 현재 등급은 " + gradeId + "입니다.");
    }

    public void createGrade(Grade grade) {
        gradeMapper.save(grade);
    }
    public void deleteGrade(long gradeId) {
        gradeMapper.deleteById(gradeId);
    }
}

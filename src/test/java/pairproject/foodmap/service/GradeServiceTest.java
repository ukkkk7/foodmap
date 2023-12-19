package pairproject.foodmap.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pairproject.foodmap.domain.Grade;
import pairproject.foodmap.domain.User;
import pairproject.foodmap.repository.UserMapper;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GradeServiceTest {
    @InjectMocks
    private GradeService gradeService;
    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Test
    @DisplayName("등급 업데이트 테스트_100미만")
    public void gradeUpdateTest_LessThan100() {
        //given
        int followerCount = 80;
        long userId = 1L;

        //when
        gradeService.updateGrade(followerCount, userId);

        //then
        verify(userService, never()).updateUserGrade(Grade.LEV1, userId);
    }

    @Test
    @DisplayName("등급 업데이트 테스트_500 이상 1000미만")
    public void gradeUpdateTest_Between500And1000() {
        //given
        int followerCount = 800;
        long userId = 1L;
        Grade grade = Grade.LEV4;

        //when
        gradeService.updateGrade(followerCount, userId);

        //then
        verify(userService, times(1)).updateUserGrade(grade, userId);
    }


}

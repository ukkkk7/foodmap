package pairproject.foodmap.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pairproject.foodmap.repository.SubscribeMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscribeServiceTest {
    @InjectMocks
    private SubscribeService subscribeService;
    @Mock
    private SubscribeMapper subscribeMapper;
    @Mock
    private GradeService gradeService;

    @Test
    @DisplayName("구독 테스트")
    public void subscribeTest() {
        //given
        long following = 1L;
        long follower = 2L;
        int followerCount = 100;
        when(subscribeService.getFollowerCount(following)).thenReturn(followerCount);

        //when
        subscribeService.createSubscribe(following, follower);

        //then
        verify(subscribeMapper, times(1)).save(following, follower);
        verify(gradeService, times(1)).updateGrade(followerCount, following);
    }

    @Test
    @DisplayName("구독 취소 테스트")
    public void subscribeRevokeTest() {
        //given
        long subscribeId = 1L;

        //when
        subscribeService.deleteSubscribeById(subscribeId);

        //then
        verify(subscribeMapper, times(1)).deleteById(subscribeId);
    }

    @Test
    @DisplayName("팔로우 수가 없는 경우 테스트")
    public void noFollowerCountTest() {
        //given
        long userId = 1L;
        when(subscribeMapper.findFollowerCount(userId)).thenReturn(null);

        //when
        int followerCount = subscribeService.getFollowerCount(userId);

        //then
        assertEquals(followerCount, 0);
    }


    @Test
    @DisplayName("팔로우 수가 있는 경우 테스트")
    public void FollowerCountTest() {
        //given
        long userId = 1L;
        int followerCount = 111;
        when(subscribeMapper.findFollowerCount(userId)).thenReturn(followerCount);

        //when
        int newFollowerCount = subscribeService.getFollowerCount(userId);

        //then
        assertEquals(newFollowerCount, followerCount);
    }

    @Test
    @DisplayName("(내가) 팔로우 한 사람 목록 테스트")
    public void followPeopleListTest() {
        //given
        long userId = 1L;
        List<Long> people = List.of(2L, 3L, 8L, 4L, 6L);
        when(subscribeMapper.findFollowingList(userId)).thenReturn(people);

        //when
        List<Long> followingList = subscribeService.getFollowingList(userId);

        //then
        assertEquals(followingList, people);
    }

    @Test
    @DisplayName("새글 작성 시 구독한 사람들에게 알람 테스트")
    public void sendAlarmTest() {
        //given
        long userId = 1L;

        //when
        subscribeService.sendAlarm(userId);

        //then
        verify(subscribeMapper, times(1)).findFollowerList(userId);
    }
}

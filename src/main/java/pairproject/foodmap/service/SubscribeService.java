package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pairproject.foodmap.repository.SubscribeMapper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SubscribeService {
    private final SubscribeMapper subscribeMapper;
    private final GradeService gradeService;

    public void createSubscribe(long following, long follower) {
        subscribeMapper.save(following, follower);
        gradeService.updateGrade(getFollowerCount(following), following);
    }

    public void deleteSubscribeById(long subscribeId) {
        subscribeMapper.deleteById(subscribeId);
    }

    public int getFollowerCount(long userId) {
        Integer followerCount = subscribeMapper.findFollowerCount(userId);
        if (followerCount == null) {
            return 0;
        }
        return followerCount;
    }

    public List<Long> getFollowingList(long userId) {
        return subscribeMapper.findFollowingList(userId);
    }

    public void sendAlarm(long userId) {
        List<Long> followerList = subscribeMapper.findFollowerList(userId);
        String title = "[👾]" + userId + "님께서 새 글을 포스트 하였습니다.";

        for (Long follower : followerList) {
            log.info("{}님, {}", follower, title);
        }
    }
}

package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pairproject.foodmap.repository.SubscribeMapper;
import pairproject.foodmap.util.AlarmUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscribeService {
    private final SubscribeMapper subscribeMapper;
    private final GradeService gradeService;
    private final AlarmUtil alarmUtil;

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

    public List<Long> getFollowerList(long userId) {
        return subscribeMapper.findFollowerList(userId);
    }

    public void sendAlarm(long userId) {
        List<Long> followerList = getFollowerList(userId);
        String title = "[👾]" + userId + "님께서 새 글을 포스트 하였습니다.";

        for (Long follower : followerList) {
            System.out.println(follower + "님, " + title);
//            alarmUtil.send(follower.toString(), title); 프론트 부재로 인한 보류
        }
    }
}

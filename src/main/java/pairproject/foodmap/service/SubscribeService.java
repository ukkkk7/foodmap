package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pairproject.foodmap.repository.SubscribeMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscribeService {
    private final SubscribeMapper subscribeMapper;
    public void createSubscribe(long following, long follower) {
        subscribeMapper.save(following, follower);
    }

    public void deleteSubscribeById(long subscribeId) {
        subscribeMapper.deleteById(subscribeId);
    }
}

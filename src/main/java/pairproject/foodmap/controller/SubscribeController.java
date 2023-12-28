package pairproject.foodmap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pairproject.foodmap.service.SubscribeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubscribeController {
    private final SubscribeService subscribeService;

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeCreate(@RequestParam long following,
                                                  @RequestParam long follower) {
        subscribeService.createSubscribe(following, follower);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/subscribe/{subscribeId}")
    public ResponseEntity<String> subscribeDelete(@PathVariable long subscribeId) {
        subscribeService.deleteSubscribeById(subscribeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/subscribe/follower") //나를 구독한 사람 수
    public ResponseEntity<Integer> followerCount(@RequestParam long userId) { //인증된 사용자
        int count = subscribeService.getFollowerCount(userId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/subscribe/following") //내가 구독한 사람들 목록
    public ResponseEntity<List<Long>> followingList(@RequestParam long userId) { //인증된 사용자
        List<Long> followingList = subscribeService.getFollowingList(userId);
        //List<Long> 에서 List<UserDto>로 변경필요 -> 사용자 구현 담당자 미지정으로 인한 보류
        return new ResponseEntity<>(followingList, HttpStatus.OK);
    }
}

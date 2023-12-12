package pairproject.foodmap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pairproject.foodmap.service.SubscribeService;

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
}

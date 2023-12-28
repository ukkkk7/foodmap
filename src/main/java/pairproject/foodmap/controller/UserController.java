package pairproject.foodmap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pairproject.foodmap.domain.User;
import pairproject.foodmap.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>("가입되었습니다.", HttpStatus.OK);
    }
}


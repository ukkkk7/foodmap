package pairproject.foodmap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pairproject.foodmap.dto.UserDto;
import pairproject.foodmap.service.AdminService;

import java.util.List;


@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminService adminService;

    //전제 회원 리스트
    @GetMapping("/user/all")
    public ResponseEntity<List> userList(){

            List<UserDto> userList = adminService.getUserList();
            return ResponseEntity.ok().body(userList);
    }

    //특정 회원 삭제
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> userDelete(@PathVariable long id) {

        adminService.deleteUser(id);
        return ResponseEntity.ok("회원이 삭제되었습니다");
    }


    //회원 검색
    @GetMapping("/user/{name}")
    public ResponseEntity<List> userSearch(@PathVariable String name){

        List<UserDto> userSearch = adminService.searchUser(name);

       return ResponseEntity.ok(userSearch);

    }
}

package pairproject.foodmap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pairproject.foodmap.dto.BookmarkVO;
import pairproject.foodmap.service.BookmarkService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;

    //즐겨찾기 등록 메소드
    @PostMapping("/bookmark/add")
    public ResponseEntity<String> bookmarkCreate(@RequestBody Map<String, Object> paramMap){

        bookmarkService.createBookmark(paramMap);

        return ResponseEntity.ok().body("즐겨찾기 등록이 완료되었습니다.");
    }

    //즐겨찾기 해체 메소드
    @DeleteMapping("/bookmark")
    public ResponseEntity<String> bookmarkDelete(@RequestParam long id){
        bookmarkService.deleteBookmark(id);
        return ResponseEntity.ok().body("즐겨찾기가 삭제되었습니다.");
    }



    //즐겨찾기 목록 리스트(가나다 순 정렬)
    @GetMapping("/bookmark/list")
    public ResponseEntity<List> bookmarkList(@RequestParam long userId){
        List<BookmarkVO> getBookmarkList = bookmarkService.getBookmarkList(userId);
        return ResponseEntity.ok().body(getBookmarkList);
    }


    //선택한 즐겨찾기 삭제 메소드
    @DeleteMapping("/bookmark/select")
    public ResponseEntity<String> bookmarkSelectedDelete(@RequestParam("bookmarkIds") List<Long> bookmarkIds){


        bookmarkService.deleteSelectedBookmark(bookmarkIds);


        return ResponseEntity.ok().body("선택한 즐겨찾기 삭제가 완료되었습니다.");
    }







}

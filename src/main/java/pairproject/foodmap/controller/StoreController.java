package pairproject.foodmap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pairproject.foodmap.dto.StoreDetailVO;
import pairproject.foodmap.service.StoreLikeService;
import pairproject.foodmap.service.StoreSearchService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/store")
@RestController
public class StoreController {

    private final StoreSearchService storeSearchService;
    private final StoreLikeService storeLikeService;


    //음식점 검색 메소드
    @GetMapping("/search")
    public ResponseEntity<List> getStoreList(@RequestParam Map<String, Object> param) {

            List<String> getList = storeSearchService.getStoreList(param);
            List<String> result;

        // 사용자가 필터를 눌렀을 때 "order" 파라미터에 따라 정렬 방식을 선택
        if (param.containsKey("order")) {
            String order = (String) param.get("order");
            switch (order) {
                case "거리순":
                    result = storeSearchService.getNearList(param);
                    break;
                case "좋아요순":
                    result = storeSearchService.getLikeCntList(param);
                    break;
                default:
                    result = getList;
                    break;
            }
        } else {
            result = getList;
        }

            if (result == null || result.isEmpty()) {

                return ResponseEntity.ok().body(Collections.singletonList("검색 결과가 없습니다."));
            }

            return ResponseEntity.ok().body(result);


    }

    //음식점 상세페이지 조회 메소드
    @GetMapping("/search/detail")
    public ResponseEntity<List<StoreDetailVO>> getStoreDetailList(@RequestParam Map<String, Object> param) {

        List<StoreDetailVO> getDetailList = storeSearchService.getStoreDetailList(param);

        return ResponseEntity.ok().body(getDetailList);

    }


    //좋아요 추가 메소드
    @PostMapping("/search/like/add")
    public ResponseEntity createStorelike(@RequestParam Map<String, Object> param){


        storeLikeService.createStoreLike(param);

        return ResponseEntity.ok().build();
    }

    //좋아요 삭제 메소드
    @DeleteMapping("/search/like/delete")
    public ResponseEntity deleteStorelike(@RequestParam Map<String, Object> param){

        storeLikeService.deleteStoreLike(param);

        return ResponseEntity.ok().build();
    }


}

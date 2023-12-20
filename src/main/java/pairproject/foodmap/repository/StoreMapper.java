package pairproject.foodmap.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;
import pairproject.foodmap.dto.StoreDetailVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface StoreMapper {

    //기본 검색시 키워드에 맞는 음식점 리스트
    List<String> getStoreList(@RequestParam Map<String, Object> param);

    //가까운 거리 순 리스트
    List<String> getNearList(@RequestParam Map<String, Object> param);

    //좋아요 많은 순 리스트
    List<String> getLikeCntList(@RequestParam Map<String, Object> param);

    //음식점 상세정보 리스트
    List<StoreDetailVO> getStoreDetailList(Map<String, Object> param);

    void createStoreLike(Map<String, Object> param);

    void deleteStoreLike(Map<String, Object> param);
}

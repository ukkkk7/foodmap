package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pairproject.foodmap.dto.StoreDetailVO;
import pairproject.foodmap.repository.StoreMapper;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StoreSearchService {

    private final StoreMapper storeMapper;

    public List<String> getStoreList(@RequestParam Map<String, Object> param) {

        return storeMapper.getStoreList(param);

    }

    public List<String> getNearList(@RequestParam Map<String, Object> param) {
        return storeMapper.getNearList(param);
    }

    public List<String> getLikeCntList(@RequestParam Map<String, Object> param) {
        return storeMapper.getLikeCntList(param);
    }


    public List<StoreDetailVO> getStoreDetailList(Map<String, Object> param) {
        return storeMapper.getStoreDetailList(param);
    }
}

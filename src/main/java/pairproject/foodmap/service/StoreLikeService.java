package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pairproject.foodmap.repository.StoreMapper;

import java.util.Map;
@RequiredArgsConstructor
@Service
public class StoreLikeService {

    private final StoreMapper storeMapper;
    public void createStoreLike(Map<String, Object> param) {
        storeMapper.createStoreLike(param);
    }

    public void deleteStoreLike(Map<String, Object> param) {
        storeMapper.deleteStoreLike(param);
    }
}

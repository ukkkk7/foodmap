package pairproject.foodmap.repository;

import org.apache.ibatis.annotations.Mapper;

import pairproject.foodmap.dto.BookmarkVO;


import java.util.List;
import java.util.Map;

@Mapper
public interface BookmarkMapper {
    void createBookmark(Map<String, Object> paramMap);

    void deleteBookmark(long id);

    List<BookmarkVO> getBookmarkList(long userId);

    void deleteSelectedBookmark(List<Long> bookmarkIds);
}

package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import pairproject.foodmap.dto.BookmarkVO;
import pairproject.foodmap.repository.BookmarkMapper;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final BookmarkMapper bookmarkMapper;
    public void createBookmark(Map<String, Object> paramMap) {

        bookmarkMapper.createBookmark(paramMap);
    }

    public void deleteBookmark(long id) {
        bookmarkMapper.deleteBookmark(id);
    }

    public List<BookmarkVO> getBookmarkList(long userId) {
        return bookmarkMapper.getBookmarkList(userId);
    }

    public void deleteSelectedBookmark(List<Long> bookmarkIds) {
        bookmarkMapper.deleteSelectedBookmark(bookmarkIds);
    }
}

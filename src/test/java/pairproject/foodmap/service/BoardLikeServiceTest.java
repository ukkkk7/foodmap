package pairproject.foodmap.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pairproject.foodmap.domain.Board;
import pairproject.foodmap.repository.BoardLikeMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardLikeServiceTest {
    @InjectMocks
    private BoardLikeService boardLikeService;
    @Mock
    private BoardLikeMapper boardLikeMapper;
    @Mock
    private BoardService boardService;

    @Test
    @DisplayName("게시글 좋아요 추가 테스트")
    public void boardLikeAddTest() {
        //given
        long userId = 1L;
        long boardId = 1L;

        //when
        boardLikeService.createBoardLike(userId, boardId);

        //then
        verify(boardLikeMapper, times(1)).save(userId, boardId);
    }

    @Test
    @DisplayName("게시글 좋아요 취소 테스트")
    public void boardLikeRevokeTest() {
        //given
        long userId = 1L;
        long boardId = 1L;

        //when
        boardLikeService.deleteBoardLike(userId, boardId);

        //then
        verify(boardLikeMapper, times(1)).delete(userId, boardId);
    }

    @Test
    @DisplayName("사용자가 좋아요 한 게시글 목록 테스트")
    public void BoardLikeListByUser() {
        //given
        long userId = 1L;
        List<Long> boards = new ArrayList<>(List.of(1L, 2L, 3L, 4L, 5L));
        when(boardLikeMapper.findBoardIdAllByUserId(userId)).thenReturn(boards);
        when(boardService.getBoardById(anyLong()))
                .thenReturn(new Board());

        //when
        List<Board> newBoards = boardLikeService.getBoardIdAllByUserId(userId);

        //then
        assertEquals(newBoards.size(), boards.size());
    }


    @Test
    @DisplayName("게시글 좋아요 수 테스트")
    public void boardLikeCountTest() {
        //given
        long boardId = 1L;
        int count = 13;
        when(boardLikeMapper.findBoardLikeCount(boardId)).thenReturn(count);

        //when
        int boardLikeCount = boardLikeService.getBoardLikeCount(boardId);

        //then
        assertEquals(boardLikeCount, count);
    }

}

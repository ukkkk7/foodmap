package pairproject.foodmap.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pairproject.foodmap.domain.Board;
import pairproject.foodmap.exception.DataAccessException;
import pairproject.foodmap.repository.BoardMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {
    @InjectMocks
    private BoardService boardService;
    @Mock
    private BoardMapper boardMapper;

    private Board createBoardRequest() {
        return Board.builder()
                .boardId(1)
                .title("test")
                .content("test")
                .regDate(LocalDateTime.now())
                .userId(1)
                .storeId(1).build();
    }

    @Test
    @DisplayName("게시글 추가 테스트")
    public void boardAddTest() {
        //given
        Board board = createBoardRequest();
        doNothing().when(boardMapper).saveAndGetId(board);
        when(boardService.getBoardById(board.getBoardId())).thenReturn(board);

        //when
        Board newBoard = boardService.createBoard(board);

        //then
        verify(boardMapper, times(1)).saveAndGetId(board);
        assertEquals(board.getTitle(), newBoard.getTitle());
        assertEquals(board.getRegDate(), newBoard.getRegDate());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void boardEditTest() {
        //given
        Board updateBoard = createBoardRequest();
        long boardId = updateBoard.getBoardId();

        when(boardMapper.edit(updateBoard, boardId)).thenReturn(1);
        when(boardMapper.findById(boardId)).thenReturn(updateBoard);

        //when
        Board board = boardService.updateBoard(updateBoard, boardId);

        //then
        assertEquals(board.getTitle(), updateBoard.getTitle());
        verify(boardMapper, times(1)).edit(updateBoard, boardId);
        verify(boardMapper, times(1)).findById(boardId);
    }


    @Test
    @DisplayName("게시글 삭제 테스트")
    public void boardDeleteTest() {
        //given
        long boardId = 1L;
        when(boardMapper.deleteById(boardId)).thenReturn(0);

        //when
        assertThrows(DataAccessException.class, () -> boardService.deleteBoard(boardId));

        //then
        verify(boardMapper, times(1)).deleteById(boardId);
    }

    @Test
    @DisplayName("게시글 아이디(pk) 목록 테스트")
    public void boardIdListTest() {
        //given
        Board board = createBoardRequest();
        long storeId = board.getStoreId();
        long boardId = board.getBoardId();
        List<Long> boardIdList = Arrays.asList(1L, 2L, 3L);
        when(boardService.getBoardById(boardId)).thenReturn(board);
        when(boardMapper.findBoardIdByStoreId(storeId)).thenReturn(boardIdList);

        //when
        List<Long> boardIdAll = boardService.getBoardIdAll(boardId);

        //then
        verify(boardMapper, times(1)).findBoardIdByStoreId(storeId);
        assertEquals(boardIdAll.size(), boardIdList.size());
    }

}

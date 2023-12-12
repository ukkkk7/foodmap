package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pairproject.foodmap.domain.Board;
import pairproject.foodmap.repository.BoardMapper;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    public Board createBoard(Board board) {
        boardMapper.saveAndGetId(board);
        return getBoardById(board.getBoardId());
    }

    public Board getBoardById(long boardId) {
        return boardMapper.findById(boardId);
    }

    public List<Board> getBoardAll() {
        return boardMapper.findAll();
    }

    public Board updateBoard(Board board, long boardId) {
        int returnValue = boardMapper.edit(board, boardId);
        if (returnValue == 1){
            return boardMapper.findById(boardId);
        }
        throw new RuntimeException("[Exception] Board 수정 실패");
    }

    public void deleteBoard(long boardId) {
        int returnValue = boardMapper.deleteById(boardId);
        if (returnValue != 1) {
            throw new RuntimeException("[Exception] Board 삭제 실패");
        }
    }

    public List<Long> getBoardIdAll(long boardId) {
        long storeId = getBoardById(boardId).getStoreId();
        return boardMapper.findBoardIdByStoreId(storeId);
    }
}

package pairproject.foodmap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pairproject.foodmap.domain.Board;
import pairproject.foodmap.domain.BoardLike;
import pairproject.foodmap.repository.BoardLikeMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardLikeService {
    private final BoardLikeMapper boardLikeMapper;
    private final BoardService boardService;

    public void createBoardLike(long userId, long boardId) {
        boardLikeMapper.save(userId, boardId);
    }

    public void deleteBoardLike(long userId, long boardId) {
        boardLikeMapper.delete(userId, boardId);
    }

    public List<Board> getBoardIdAllByUserId(long userId) {
        List<Long> boardIdList = boardLikeMapper.findBoardIdAllByUserId(userId);
        List<Board> boardList = new ArrayList<>();
        for (Long boardId : boardIdList) {
            Board board = boardService.getBoardById(boardId);
            boardList.add(board);
        }
        return boardList;
    }

    public int getBoardLikeCount(long boardId) {
        return boardLikeMapper.findBoardLikeCount(boardId);
    }
}

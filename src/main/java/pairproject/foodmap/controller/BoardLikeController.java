package pairproject.foodmap.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pairproject.foodmap.domain.Board;
import pairproject.foodmap.dto.BoardDto;
import pairproject.foodmap.service.BoardLikeService;

import java.util.List;

/**
 * 나중에 인증, 권한 기능 구현 시 userId 변경필요
 */
@RestController
@RequiredArgsConstructor
public class BoardLikeController {
    private final BoardLikeService boardLikeService;
    private final ModelMapper modelMapper;

    @PostMapping("/boards/{boardId}/like")
    public ResponseEntity<String> boardLike(@RequestParam("user") long userId, @RequestParam("board") long boardId) {
        boardLikeService.createBoardLike(userId, boardId);
        return new ResponseEntity<>("좋아요 성공", HttpStatus.OK);
    }

    @PostMapping("/boards/{boardId}/hate")
    public ResponseEntity<String> boardHate(@RequestParam("user") long userId, @RequestParam("board") long boardId) {
        boardLikeService.deleteBoardLike(userId, boardId);
        return new ResponseEntity<>("싫어요 성공", HttpStatus.OK);
    }

    @GetMapping("/boards/like")
    public ResponseEntity<List<BoardDto>> boardListByLike(@RequestParam("user") long userId) {
        List<Board> boardList = boardLikeService.getBoardIdAllByUserId(userId);
        List<BoardDto> boardDtoList = boardList.stream()
                .map(board -> modelMapper.map(board, BoardDto.class))
                .toList();
        return new ResponseEntity<>(boardDtoList, HttpStatus.OK);
    }

    @GetMapping("/boards/{boardId}/like")
    public ResponseEntity<Integer> boardLikeCount(@PathVariable long boardId) {
        int boardLikeCount = boardLikeService.getBoardLikeCount(boardId);
        return new ResponseEntity<>(boardLikeCount, HttpStatus.OK);
    }
}

package pairproject.foodmap.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pairproject.foodmap.domain.Board;
import pairproject.foodmap.dto.BoardDto;
import pairproject.foodmap.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ModelMapper modelMapper;

    private BoardDto getBoardDto(Board board) {
        return modelMapper.map(board, BoardDto.class);
    }

    @PostMapping("/board")
    public ResponseEntity<BoardDto> boardCreate(@RequestPart Board board,
                                                @RequestPart List<MultipartFile> multipartFiles) {
        Board created = boardService.createBoard(board);
        return new ResponseEntity<>(getBoardDto(created), HttpStatus.OK);
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BoardDto> boardDetails(@PathVariable long boardId) {
        Board board = boardService.getBoardById(boardId);
        return new ResponseEntity<>(getBoardDto(board), HttpStatus.OK);
    }

    @GetMapping("/boards")
    public ResponseEntity<List<BoardDto>> boardList() {
        List<BoardDto> dtoList = boardService.getBoardAll().stream()
                .map(board -> modelMapper.map(board, BoardDto.class))
                .toList();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PatchMapping("/boards/{boardId}")
    public ResponseEntity<BoardDto> boardUpdate(@RequestPart Board board,
                                                @PathVariable long boardId,
                                                @RequestPart List<MultipartFile> multipartFiles) {
        Board updated = boardService.updateBoard(board, boardId);
        return new ResponseEntity<>(getBoardDto(updated), HttpStatus.OK);
    }

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<String> boardDelete(@PathVariable long boardId) {
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }
}

package pairproject.foodmap.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pairproject.foodmap.domain.Board;
import pairproject.foodmap.domain.BoardImage;
import pairproject.foodmap.dto.BoardDto;
import pairproject.foodmap.dto.BoardImageDto;
import pairproject.foodmap.service.BoardImageService;
import pairproject.foodmap.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardImageService boardImageService;
    private final ModelMapper modelMapper;

    private BoardDto getBoardDto(Board board) {
        return modelMapper.map(board, BoardDto.class);
    }

    private List<BoardImageDto> getBoardImageDto(List<BoardImage> boardImages) {
        return boardImages.stream()
                .map(boardImage -> modelMapper.map(boardImage, BoardImageDto.class))
                .toList();
    }

    @PostMapping("/board") //게시글 생성
    public ResponseEntity<BoardDto> boardCreate(@RequestPart Board board,
                                                @RequestPart("addFiles") List<MultipartFile> addFiles,
                                                @RequestPart("mainImageFile") MultipartFile mainImageFile) {
        Board created = boardService.createBoard(board); //게시글 생성
        List<BoardImage> boardImages = boardImageService.createBoardImage( //게시글 이미지 생성
                created.getBoardId(), addFiles, mainImageFile);

        //dto 변환
        BoardDto boardDto = getBoardDto(created);
        List<BoardImageDto> boardImageDto = getBoardImageDto(boardImages);
        boardDto.setBoardImages(boardImageDto);

        return new ResponseEntity<>(boardDto, HttpStatus.OK);
    }

    @GetMapping("/boards/{boardId}") //게시글 상세
    public ResponseEntity<BoardDto> boardDetails(@PathVariable long boardId) {
        Board board = boardService.getBoardById(boardId);
        return new ResponseEntity<>(getBoardDto(board), HttpStatus.OK);
    }

    @GetMapping("/boards") //게시글 전체 목록
    public ResponseEntity<List<BoardDto>> boardList() {
        List<BoardDto> dtoList = boardService.getBoardAll().stream()
                .map(board -> modelMapper.map(board, BoardDto.class))
                .toList();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping("/boards/{boardId}") //게시글 수정
    public ResponseEntity<BoardDto> boardUpdate(@RequestPart Board board,
                                                @PathVariable long boardId,
                                                @RequestPart("addFiles") List<MultipartFile> addFiles,
                                                @RequestPart("deleteFilenames") List<String> deleteFilenames) {
        List<BoardImage> boardImages = boardImageService.updateBoardImage(boardId, addFiles, deleteFilenames);
        Board updated = boardService.updateBoard(board, boardId);
        return new ResponseEntity<>(getBoardDto(updated), HttpStatus.OK);
    }

    @DeleteMapping("/boards/{boardId}") //게시글 삭제
    public ResponseEntity<String> boardDelete(@PathVariable long boardId) {
        List<String> filenames = boardImageService.getFilenameByBoardId(boardId);
        boardImageService.deleteBoardImage(filenames);
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/boards/{boardId}/main-images") //메인 이미지 목록
    public ResponseEntity<List<BoardImageDto>> MainImagesList(@PathVariable long boardId) {
        List<Long> boardIdAll = boardService.getBoardIdAll(boardId);
        List<BoardImage> boardMainImageAll = boardImageService.getBoardMainImageAll(boardIdAll);
        List<BoardImageDto> boardImageDto = getBoardImageDto(boardMainImageAll);

        return new ResponseEntity<>(boardImageDto, HttpStatus.OK);
    }
}

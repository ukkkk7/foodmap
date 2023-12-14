package pairproject.foodmap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pairproject.foodmap.domain.Board;
import pairproject.foodmap.domain.BoardImage;
import pairproject.foodmap.dto.BoardDto;
import pairproject.foodmap.dto.BoardImageDto;
import pairproject.foodmap.service.BoardImageService;
import pairproject.foodmap.service.BoardService;
import pairproject.foodmap.service.SubscribeService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardImageService boardImageService;
    private final ModelMapper modelMapper;
    private final SubscribeService subscribeService;

    private BoardDto getBoardDto(Board board) {
        return modelMapper.map(board, BoardDto.class);
    }

    private List<BoardImageDto> getBoardImageDto(List<BoardImage> boardImages) {
        return boardImages.stream()
                .map(boardImage -> modelMapper.map(boardImage, BoardImageDto.class))
                .toList();
    }

    @PostMapping("/board") //게시글 생성
    public ResponseEntity<BoardDto> boardCreate(
            @RequestPart @Validated Board board,
            BindingResult bindingResult,
            @RequestPart(value = "addFiles", required = false) List<MultipartFile> addFiles,
            @RequestPart(value = "mainImageFile", required = false) MultipartFile mainImageFile,
            @RequestParam long userId) { //인증된 사용자 변경 필요
        //게시글 생성
        Board created = boardService.createBoard(board);

        if (bindingResult.hasErrors()) {
            log.error("검증 오류 발생 : {}", bindingResult);
            throw new RuntimeException(String.valueOf(bindingResult));
        }

        //Board dto 변환
        BoardDto boardDto = getBoardDto(created);

        if (addFiles != null || mainImageFile != null) {
            List<BoardImage> boardImages = boardImageService.createBoardImage( //게시글 이미지 생성
                    created.getBoardId(), addFiles, mainImageFile);

            //이미지 dto 변환
            List<BoardImageDto> boardImageDto = getBoardImageDto(boardImages);
            boardDto.setBoardImages(boardImageDto);
        }
        subscribeService.sendAlarm(userId); //팔로워들에게 새글 알람 발송
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

    /**
     * 기존의 이미지를 삭제할 수 있음
     * 새로운 이미지를 추가할 수 있음
     * 하지만 메인 이미지는 변경이 불가
     */
    @PostMapping("/boards/{boardId}") //게시글 수정
    public ResponseEntity<BoardDto> boardUpdate(
            @RequestPart Board board,
            @PathVariable long boardId,
            @RequestPart(value = "addFiles", required = false) List<MultipartFile> addFiles,
            @RequestPart(value = "deleteFilenames", required = false) List<String> deleteFilenames) {

        Board updated = boardService.updateBoard(board, boardId);
        BoardDto boardDto = getBoardDto(updated);

        if (addFiles != null || deleteFilenames != null) {
            List<BoardImage> boardImages =
                    boardImageService.updateBoardImage(boardId, addFiles, deleteFilenames);

            //이미지 dto 변환
            List<BoardImageDto> boardImageDto = getBoardImageDto(boardImages);
            boardDto.setBoardImages(boardImageDto);
        }
            return new ResponseEntity<>(boardDto, HttpStatus.OK);
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

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
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBoardController {

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
}

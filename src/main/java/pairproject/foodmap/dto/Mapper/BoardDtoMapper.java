package pairproject.foodmap.dto.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pairproject.foodmap.domain.Board;
import pairproject.foodmap.domain.BoardImage;
import pairproject.foodmap.dto.BoardDto;
import pairproject.foodmap.dto.BoardImageDto;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BoardDtoMapper {
    private final ModelMapper modelMapper;


    public BoardDto getBoardDto(Board board) {
        return modelMapper.map(board, BoardDto.class);
    }

    public List<BoardImageDto> getBoardImageDto(List<BoardImage> boardImages) {
        return boardImages.stream()
                .map(boardImage -> modelMapper.map(boardImage, BoardImageDto.class))
                .toList();
    }

    public BoardDto convertBoardDto(Board board, List<BoardImage> boardImageList) {
        BoardDto boardDto = getBoardDto(board);
        List<BoardImageDto> boardImageDto = getBoardImageDto(boardImageList);
        boardDto.setBoardImages(boardImageDto);

        return boardDto;
    }

}

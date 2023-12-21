package pairproject.foodmap.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardDto {
    private String title;
    private String content;
    private Date regDate;
    private List<BoardImageDto> boardImages = new ArrayList<>();
}

package pairproject.foodmap.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDto {
    private String title;
    private String content;
    private Date regDate;
}

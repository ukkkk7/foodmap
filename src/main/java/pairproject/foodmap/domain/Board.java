package pairproject.foodmap.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private long boardId;
    private String title;
    private String content;
    private Date regDate;
    private long userId;
    private long storeId;

}

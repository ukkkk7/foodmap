package pairproject.foodmap.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Board {
    private long boardId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private LocalDateTime regDate;
    @NotNull
    private long userId;
    @NotNull
    private long storeId;

    public Board() {
        this.regDate = LocalDateTime.now();
    }
}

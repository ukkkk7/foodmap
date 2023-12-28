package pairproject.foodmap.domain;

import lombok.Getter;

@Getter
public class BoardLike {
    private long likeId;
    private long userId;
    private long boardId;
}

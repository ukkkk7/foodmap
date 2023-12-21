package pairproject.foodmap.dto;

import lombok.Data;

@Data
public class BookmarkVO {

    private long bookmarkId;
    private long userId;
    private long storeId;
    private String storeName;
    private int likeCnt;

}
